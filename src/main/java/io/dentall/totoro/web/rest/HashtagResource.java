package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Hashtag;
import io.dentall.totoro.domain.enumeration.HashTagType;
import io.dentall.totoro.repository.HashtagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/hashtag")
public class HashtagResource {

    private final Logger log = LoggerFactory.getLogger(HashtagResource.class);

    private final HashtagRepository hashtagRepository;

    public HashtagResource(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<Hashtag>> getHashtags(
        @RequestParam(value = "search") String search,
        @RequestParam(value = "tagType") HashTagType tagType,
        @PageableDefault
        @SortDefault.SortDefaults({@SortDefault(sort = "referenceCount", direction = DESC)}) Pageable pageable) {

        log.debug("REST request to get hashtags, search : {}, tag type: {}", search, tagType);
        return ResponseEntity.ok(hashtagRepository.findAllByTagNameIgnoreCaseLikeAndTagType("%" + search + "%", tagType, pageable).getContent());
    }
}
