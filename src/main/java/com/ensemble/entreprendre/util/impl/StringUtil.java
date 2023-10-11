package com.ensemble.entreprendre.util.impl;

import com.ensemble.entreprendre.util.IStringUtil;
import org.springframework.stereotype.Component;

@Component
public class StringUtil implements IStringUtil {
    @Override
    public Boolean isValueSet(String string) {
        return string != null && !string.isEmpty() && !string.isBlank();
    }
}
