package com.tidal.quake;

import java.util.HashMap;
import java.util.Map;


//@Generated("org.jsonschema2pojo")
public class Metadata {

    private Long generated;
    private String url;
    private String title;
    private int status;
    private String api;
    private int count;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The generated
     */
    public Long getGenerated() {
        return generated;
    }

    /**
     *
     * @param generated
     * The generated
     */
    public void setGenerated(Long generated) {
        this.generated = generated;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The api
     */
    public String getApi() {
        return api;
    }

    /**
     *
     * @param api
     * The api
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     *
     * @return
     * The count
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}