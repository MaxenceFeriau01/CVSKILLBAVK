package com.ensemble.entreprendre.domain.enumeration;

public enum OfferTypeEnum {
    PROFESSIONAL_CONTRACT, LEARNING_CONTRACT;

    public String getValue() {
        String value;
        switch (this.toString()) {
            case "PROFESSIONAL_CONTRACT":
                value = "Contrat de professionnalisation";
                break;
            case "LEARNING_CONTRACT":
                value = "Contrat d'apprentissage";
                break;
            default:
                value = "Autre contrat";
                break;
        }
        return value;
    }
}
