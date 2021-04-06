package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.business.vm.ImageRelationPathVM;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.dentall.totoro.repository.ImageRelationRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Profile({"img-host", "img-gcs"})
@RestController
@RequestMapping("/api")
public class ImageRelationResource {

    private final Logger log = LoggerFactory.getLogger(ImageRelationResource.class);

    private static final String ENTITY_NAME = "imageRelation";

    private final ImageRelationBusinessService imageRelationBusinessService;

    private final ImageBusinessService imageBusinessService;

    private final ImageRelationRepository imageRelationRepository;

    public ImageRelationResource(
        ImageRelationBusinessService imageRelationBusinessService,
        ImageBusinessService imageBusinessService,
        ImageRelationRepository imageRelationRepository
    ) {
        this.imageRelationBusinessService = imageRelationBusinessService;
        this.imageBusinessService = imageBusinessService;
        this.imageRelationRepository = imageRelationRepository;
    }

    @PostMapping("/image-relations")
    public ResponseEntity<ImageRelation> createImageRelation(@Valid @RequestBody ImageRelation imageRelation) throws URISyntaxException {
        ImageRelation result = imageRelationBusinessService.createImageRelation(imageRelation);

        return ResponseEntity.created(new URI("/api/image-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/image-relations/images")
    public ResponseEntity<List<ImageRelationPathVM>> getImagePathsByDomain(@RequestParam(value = "domain") ImageRelationDomain domain, @RequestParam(value = "domainId") Long domainId) {
        return ResponseEntity.ok(imageRelationBusinessService.getImageRelationPathsByDomain(domain, domainId));
    }

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
     *
     * @param id the id of the image-relation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/image-relations/{id}")
    public ResponseEntity<Void> deleteImageRelation(@PathVariable Long id) {
        log.debug("REST request to delete ImageRelation : {}", id);
        imageRelationBusinessService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
