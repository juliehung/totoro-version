package io.dentall.totoro.service;

import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.repository.HolidayRepository;
import io.dentall.totoro.service.dto.HolidayDTO;
import io.dentall.totoro.service.mapper.HolidayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayService.class);

    private final String url = "https://data.ntpc.gov.tw/api/datasets/308DCD75-6434-45BC-A95F-584DA4FED251/json";

    private final int size = 130; // 取最大假日天數

    private final int baseYear = 2013; // api返回資料裡，最久年份為2013，所以當做基準年，用來推估之後的年份的起始頁

    private final int maxRecursiveCount = 4;

    private final RestTemplate restTemplate;

    private final HolidayRepository holidayRepository;

    public HolidayService(RestTemplate restTemplate, HolidayRepository holidayRepository) {
        this.restTemplate = restTemplate;
        this.holidayRepository = holidayRepository;
    }

    public List<Holiday> getHolidays(Year year) {
        return findHolidays(year);
    }

    private List<Holiday> findHolidays(Year year) {
        List<Holiday> holidayList = holidayRepository.findByYear(valueOf(year.getValue()));

        if (holidayList.size() == 0) {
            int page = getPage(year);
            int size = getSize();
            holidayList = getHolidayFromGov(year, page, size, 1);
            holidayRepository.saveAll(holidayList);
        }

        return holidayList;
    }

    @SuppressWarnings("rawtypes")
    private List<Holiday> getHolidayFromGov(Year year, int page, int size, int recursiveCount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create(getUrl(page, size)));
        ResponseEntity<List<HolidayDTO>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<HolidayDTO>>() {
        });

        List<Holiday> result = HolidayMapper.INSTANCE.mapToHoliday(response.getBody());

        if (result.size() == 0) {
            return emptyList();
        }

        List<Integer> years = result.stream().map(Holiday::getYear).map(Integer::parseInt).distinct().collect(toList());

        if (!years.contains(year.getValue()) && recursiveCount < maxRecursiveCount) {
            Optional<Integer> found = years.stream().filter(y -> y < year.getValue()).findAny();
            if (found.isPresent()) {
                return getHolidayFromGov(year, page + 1, size, recursiveCount + 1); // 表示查到的資料的年份都比查詢的年份早，所以 page + 1 往後繼續找
            } else {
                return getHolidayFromGov(year, page - 1, size, recursiveCount + 1); // 表示查到的資料的年份都比查詢的年份晚，所以 page - 1 往前繼續找
            }
        }

        List<Holiday> resultOfTheYear = result.stream().filter(h -> parseInt(h.getYear()) == year.getValue()).collect(toList());
        List<Holiday> resultOfNextYear = result.stream().filter(h -> parseInt(h.getYear()) == year.getValue() + 1).collect(toList());

        LocalDate firstDay = getFirstDay(year);
        Optional<Holiday> firstHolidayOpt = resultOfTheYear.stream().filter(h -> h.getDate().isEqual(firstDay)).findFirst();

        if (!firstHolidayOpt.isPresent() && resultOfTheYear.size() > 0 && recursiveCount < maxRecursiveCount) { // 查詢的年份的1/1不存在，表示查詢的年份還有資料在上一頁，所以 page - 1 往前繼續找
            List<Holiday> subResult = getHolidayFromGov(year, page - 1, size, MAX_VALUE);
            resultOfTheYear.addAll(subResult);
        }

        if (resultOfTheYear.size() > 0 && resultOfNextYear.size() == 0 && recursiveCount < maxRecursiveCount) { // 查回來的資料沒有明年的資料，表示查詢的年份有可能還有資料在下一頁，所以 page + 1 往後繼續找
            List<Holiday> subResult = getHolidayFromGov(year, page + 1, size, MAX_VALUE);
            resultOfTheYear.addAll(subResult);
        }

        return resultOfTheYear;
    }

    private LocalDate getFirstDay(Year year) {
        return LocalDate.ofYearDay(year.getValue(), 1);
    }

    private int getPage(Year year) {
        return year.getValue() - this.baseYear - 1; // 推估欲取得年份的起始頁
    }

    private int getSize() {
        return this.size;
    }

    private String getUrl(int page, int size) {
        return this.url + "?page=" + page + "&size=" + size;
    }
}
