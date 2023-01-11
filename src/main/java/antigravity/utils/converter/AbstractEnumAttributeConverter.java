package antigravity.utils.converter;


import antigravity.utils.CommonType;
import org.springframework.util.ObjectUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class AbstractEnumAttributeConverter<E extends Enum<E> & CommonType> implements AttributeConverter<E, Integer> {

    private Class<E> targetEnumClass;
    private boolean nullable;
    private String enumName;

    @Override
    public Integer convertToDatabaseColumn(E attribute) {
        if (!nullable && attribute == null) {
            throw new IllegalArgumentException(String.format("%s(은)는 NULL로 지정할 수 없습니다.", enumName));
        }
        return EnumValueConvert.toStateCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(Integer dbData) {
        if (!nullable && ObjectUtils.isEmpty(dbData)) {
            throw new IllegalArgumentException(String.format(
                    "%s(이)가 DB에 NULL 혹은 Empty로(%s) 저장되어 있습니다.", enumName, dbData));
        }
        return EnumValueConvert.ofStateCode(targetEnumClass, dbData);
    }
}