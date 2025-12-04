package deserializationMethodsDemo;

import java.util.List;

public class coursesOffered {
    private List<courseWebAutomation> webAutomation;
    private List<courseApi> api;
    private List<courseMobile> mobile;

    public List<courseWebAutomation> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<courseWebAutomation> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<courseApi> getApi() {
        return api;
    }

    public void setApi(List<courseApi> api) {
        this.api = api;
    }

    public List<courseMobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<courseMobile> mobile) {
        this.mobile = mobile;
    }



}
