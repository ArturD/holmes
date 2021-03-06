package com.wikia.classifier.filters.text;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.wikia.classifier.filters.FilterBase;
import com.wikia.classifier.util.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddClassToMatrixFilter extends FilterBase<Matrix, Matrix> {
    private static final long serialVersionUID = -1444279996817070989L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(AddClassToMatrixFilter.class);
    private List<String> features;
    private Multimap<String, String> classMap;

    public AddClassToMatrixFilter(List<String> features, Multimap<String, String> classMap) {
        super(Matrix.class, Matrix.class);
        this.features = features;
        this.classMap = classMap;
    }

    @Override
    protected Matrix doFilter(Matrix matrix) {
        for(Map.Entry<String, Collection<String>> entry: classMap.asMap().entrySet()) {
            matrix.getRowVector(entry.getKey()).getAnnotations().add(getClassId(entry.getValue()));
        }
        return matrix;
    }

    private String getClassId(Collection<String> classes) {
        Set<String> set = Sets.newHashSet( classes );
        for (String feature : features) {
            if (set.contains(feature)) return feature;
        }
        return "other";
    }
}
