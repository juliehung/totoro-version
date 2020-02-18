package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.vm.ImageRelationPathVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageRelationResource {

    private final Logger log = LoggerFactory.getLogger(ImageRelationResource.class);

    private static final String ENTITY_NAME = "imageRelation";

    private final ImageRelationBusinessService imageRelationBusinessService;

    public ImageRelationResource(ImageRelationBusinessService imageRelationBusinessService) {
        this.imageRelationBusinessService = imageRelationBusinessService;
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
