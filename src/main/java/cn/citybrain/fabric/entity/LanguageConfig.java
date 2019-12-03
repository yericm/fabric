package cn.citybrain.fabric.entity;

import java.util.Objects;

/**
 * created by yeric on 2019/12/3
 */
public class LanguageConfig {
    private String lang;
    private boolean isDefault;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageConfig that = (LanguageConfig) o;
        return Objects.equals(lang, that.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lang);
    }
}
