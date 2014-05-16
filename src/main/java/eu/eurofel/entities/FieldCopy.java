package eu.eurofel.entities;

import org.apache.tapestry5.Field;

public class FieldCopy implements Field {
    private String clientId;
    private String controlName;
    private String label;
    private boolean disabled;
    private boolean required;

    public FieldCopy(Field field) {
        clientId = field.getClientId();
        controlName = field.getControlName();
        label = field.getLabel();
        disabled = field.isDisabled();
        required = field.isRequired();
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getControlName() {
        return controlName;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
