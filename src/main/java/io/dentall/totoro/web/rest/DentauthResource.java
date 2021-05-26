package io.dentall.totoro.web.rest;

import io.dentall.totoro.domain.DentauthToken;
import io.dentall.totoro.service.DentauthService;
import io.dentall.totoro.service.mapper.DentauthTokenMapper;
import io.dentall.totoro.web.rest.vm.DentauthTokenVM;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/dentauth")
public class DentauthResource {

    private final DentauthService dentauthService;

    public DentauthResource(DentauthService dentauthService) {
        this.dentauthService = dentauthService;
    }

    @GetMapping("/obtain-token")
    public ResponseEntity<DentauthTokenVM> obtainTokenByGet(
        @RequestParam(value = "forceCreate", defaultValue = "false") Boolean forceCreate) {
        return ResponseEntity.ok().body(doObtainToken(forceCreate));
    }

    @PostMapping("/obtain-token")
    public ResponseEntity<DentauthTokenVM> obtainTokenByPost(
        @RequestParam(value = "forceCreate", defaultValue = "false") Boolean forceCreate) {
        return ResponseEntity.ok().body(doObtainToken(forceCreate));
    }

    private DentauthTokenVM doObtainToken(boolean forceCreate) {
        DentauthToken dentauthToken = dentauthService.obtainToken(forceCreate);
        DentauthTokenVM dentauthTokenVM = DentauthTokenMapper.mapper.mapToVM(dentauthToken);
        return dentauthTokenVM;
    }

    @PostMapping("/revoke-token")
    public ResponseEntity<DentauthTokenVM> revoke() {
        DentauthToken dentauthToken = dentauthService.revokeToken();
        DentauthTokenVM dentauthTokenVM = DentauthTokenMapper.mapper.mapToVM(dentauthToken);
        return ResponseEntity.ok().body(dentauthTokenVM);
    }
}
