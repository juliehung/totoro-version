package io.dentall.totoro.business.vm.nhi;

import java.util.List;

public class NhiRuleCheckResultVM {

    private boolean validated;

    private List<String> messages;

    public NhiRuleCheckResultVM validated(boolean validated) {
        this.validated = validated;
        return this;
    }

    public NhiRuleCheckResultVM messages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public NhiRuleCheckResultVM appendMessage(String message) {
        this.messages.add(message);
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
}
