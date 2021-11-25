package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.business.vm.ImageRelationPathVM;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.ImageRelationRepository;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.ImageVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 2021.12.02 改使用 {@link PatientDocumentResource} 進行檔案操作
 */
@Deprecated
@Profile({"img-host", "img-gcs"})
@RestController
@RequestMapping("/api")
public class ImageRelationResource {

    private final Logger log = LoggerFactory.getLogger(ImageRelationResource.class);

    private static final String ENTITY_NAME = "imageRelation";

    private final ImageRelationBusinessService imageRelationBusinessService;

    private final ImageBusinessService imageBusinessService;

    private final ImageRelationRepository imageRelationRepository;

    private final DisposalRepository disposalRepository;

    public ImageRelationResource(
        ImageRelationBusinessService imageRelationBusinessService,
        ImageBusinessService imageBusinessService,
        ImageRelationRepository imageRelationRepository,
        DisposalRepository disposalRepository
    ) {
        this.imageRelationBusinessService = imageRelationBusinessService;
        this.imageBusinessService = imageBusinessService;
        this.imageRelationRepository = imageRelationRepository;
        this.disposalRepository = disposalRepository;
    }


    /**
     * 2021.12.02 改使用 {@link PatientDocumentResource#createDocument(Long, Long, MultipartFile)} 進行檔案上傳
     *
     * @param imageRelation
     * @return
     * @throws URISyntaxException
     */
    @Deprecated
    @PostMapping("/image-relations")
    public ResponseEntity<ImageRelation> createImageRelation(@Valid @RequestBody ImageRelation imageRelation) throws URISyntaxException {
        ImageRelation result = imageRelationBusinessService.createImageRelation(imageRelation);

        return ResponseEntity.created(new URI("/api/image-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * 2021.12.02 改使用 {@link PatientDocumentResource#getDocuments(Long, Long, String, Pageable)} 進行檔案查詢
     *
     * @param domain
     * @param domainId
     * @return
     */
    @Deprecated
    @GetMapping("/image-relations/images")
    public ResponseEntity<List<ImageRelationPathVM>> getImagePathsByDomain(@RequestParam(value = "domain") ImageRelationDomain domain, @RequestParam(value = "domainId") Long domainId) {
        return ResponseEntity.ok(imageRelationBusinessService.getImageRelationPathsByDomain(domain, domainId));
    }

    /**
     * 2021.12.02 改使用 {@link PatientDocumentResource#getDocuments(Long, Long, String, Pageable)} 進行檔案查詢
     *
     * @param patientId
     * @param domain
     * @param pageable
     * @return
     */
    @Deprecated
    @GetMapping("/image-relations/patients/{patientId}")
    public ResponseEntity<List<ImageVM>> getImagePathsByPatient(
        @PathVariable Long patientId,
        @RequestParam(value = "domain") ImageRelationDomain domain,
        Pageable pageable
    ) {
        HttpHeaders headers = null;
        Page<ImageRelation> page = imageRelationRepository.findByDomainAndImage_Patient_IdOrderByDomainIdDesc(domain, patientId, pageable);

        List<ImageVM> vmList = page.getContent().stream()
            .map(ir -> {
                ImageVM vm = new ImageVM();

                if (ImageRelationDomain.DISPOSAL.equals(domain) &&
                    ir.getDomainId() != null &&
                    ir.getDomainId() != 0L
                ) {
                    Optional<DisposalTable> disposalTableOptional = disposalRepository.findDisposalById(ir.getDomainId());
                    if (disposalTableOptional.isPresent()) {
                        vm.setDisposalDate(disposalTableOptional.get().getDateTime());
                    }
                }

                if (ir.getImage() != null) {
                    Map<String, String> urls = imageBusinessService.getImageThumbnailsBySize(null, ir.getImage().getId(), "original");
                    vm.setUrl(urls.getOrDefault("original", ""));
                }

                vm.setImageRelationId(ir.getId());
                vm.setImage(ir.getImage());

                return vm;
            })
            .collect(Collectors.toList());

        headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/image-relations/patients/".concat(patientId.toString()));

        return ResponseEntity.ok().headers(headers).body(vmList);
    }

    /**
     * DELETE  /image-relations/:id : delete the "id" image-relation.
     * <p>
     * 2021.12.02 改使用 {@link PatientDocumentResource#getDocuments(Long, Long, String, Pageable)} 進行檔案刪除
     *
     * @param id the id of the image-relation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Deprecated
    @DeleteMapping("/image-relations/{id}")
    public ResponseEntity<Void> deleteImageRelation(@PathVariable Long id) {
        log.debug("REST request to delete ImageRelation : {}", id);
        imageRelationBusinessService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
