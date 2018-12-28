package com.magento.idea.magento2plugin.xml;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.*;
import gnu.trove.THashSet;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class XmlPsiTreeUtil {

    @Nullable
    public static XmlTag getTypeTagOfArgument(XmlElement psiArgumentValueElement) {

        XmlTag argumentTag = PsiTreeUtil.getParentOfType(psiArgumentValueElement, XmlTag.class);
        XmlTag argumentsTag = PsiTreeUtil.getParentOfType(argumentTag, XmlTag.class);
        return PsiTreeUtil.getParentOfType(argumentsTag, XmlTag.class);
    }

    public static Collection<XmlAttributeValue> findAttributeValueElements(XmlFile xmlFile,
                                                                    String tagName,
                                                                    String attributeName) {
        Collection<XmlAttributeValue> psiElements = new THashSet<>();


        XmlTag rootTag = xmlFile.getRootTag();
        if (rootTag == null) {
            return psiElements;
        }

//        Logger.getInstance("pizzatime").info("Searching for tag: " + tagName + ", with attributeName: " + attributeName + ", in xml file: " + xmlFile.toString());

        for (XmlTag tag: rootTag.findSubTags(tagName)) {
            if (tag != null) {
                XmlAttribute attribute = tag.getAttribute(attributeName);
                if (attribute != null && attribute.getValueElement() != null) {
                    psiElements.add(attribute.getValueElement());
                }
            }
        };

        return psiElements;
    }

    public static Collection<XmlAttributeValue> findAttributeValueElements(XmlFile xmlFile,
                                                                    String tagName,
                                                                    String attributeName,
                                                                    String value) {
        Collection<XmlAttributeValue> psiElements = findAttributeValueElements(xmlFile, tagName, attributeName);

        Logger.getInstance("pizzatime").info("Searching for tag: " + tagName + ", with attributeName: " + attributeName + ", with value: " + value + ", in xml file: " + xmlFile.toString());

        psiElements.removeIf(e -> e.getValue() == null || !e.getValue().equals(value));
        return psiElements;
    }
}
