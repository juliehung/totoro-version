package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ImageRelationResource {

    private final Logger logger = LoggerFactory.getLogger(ImageRelationResource.class);

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
}
