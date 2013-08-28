package interpriters;
import java.io.IOException;
import java.lang.annotation.Annotation;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

class AnnotationIncludeFilter implements TypeFilter
{

    private final Class<? extends Annotation> annotation;

    public AnnotationIncludeFilter(final Class<? extends Annotation> annotation)
    {
        this.annotation = annotation;
    }

    public boolean match(final MetadataReader metadataReader, final MetadataReaderFactory metadataReaderFactory)
            throws IOException
    {
        return metadataReader.getAnnotationMetadata().getAnnotationTypes().contains(this.annotation.getName());
    }

}
