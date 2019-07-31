package io.dentall.totoro.business.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeethGraphConfigVM {

    @JsonProperty
    private List<Character> isPermanent;

    public List<Character> getIsPermanent() {
        return this.isPermanent;
    }

    public TeethGraphConfigVM isPermanent(List<Character> isPermanent) {
        this.isPermanent = isPermanent;
        return this;
    }

    public void setIsPermanent(List<Character> isPermanent) {
        this.isPermanent = isPermanent;
    }

}
