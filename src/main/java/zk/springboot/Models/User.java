package zk.springboot.Models;

import java.util.List;

public class User {
    private List<String> tags;
    private List<String> t2;
    private String emri;

    public User(List<String> tags, List<String> t2, String emri) {
        this.tags = tags;
        this.t2 = t2;
        this.emri = emri;
    }

    public List<String> getTags(){
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getT2() {
        return t2;
    }

    public void setT2(List<String> t2) {
        this.t2 = t2;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }
}
