package com.redfox.lunchmanager.to;

import com.redfox.lunchmanager.HasIdAndName;
import com.redfox.lunchmanager.util.validation.NoHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NamedTo extends BaseTo implements HasIdAndName {

    @NotBlank
    @Size(min = 2, max = 100)
    @NoHtml
    protected String name;

    public NamedTo() {
    }

    public NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
