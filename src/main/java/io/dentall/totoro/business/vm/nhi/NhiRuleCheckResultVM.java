package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;

import java.util.ArrayList;
import java.util.List;

public class NhiRuleCheckResultVM {

    private boolean validated = true;

    private List<String> messages = new ArrayList<>();

    private List<NhiRuleCheckResultDTO> checkHistory = new ArrayList<>();

    public NhiRuleCheckResultVM validated(boolean validated) {
        this.validated = validated;
        return this;
    }

    public NhiRuleCheckResultVM messages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public NhiRuleCheckResultVM checkHistory(List<NhiRuleCheckResultDTO> checkHistory) {
        this.checkHistory = checkHistory;
        return this;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<NhiRuleCheckResultDTO> getCheckHistory() {
        return checkHistory;
    }

    public void setCheckHistory(List<NhiRuleCheckResultDTO> checkHistory) {
        this.checkHistory = checkHistory;
    }
}
