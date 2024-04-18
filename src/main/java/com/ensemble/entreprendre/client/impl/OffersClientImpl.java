package com.ensemble.entreprendre.client.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.ensemble.entreprendre.client.IOffersClient;
import com.ensemble.entreprendre.domain.Offer;
import com.ensemble.entreprendre.domain.enumeration.OfferTypeEnum;
import com.ensemble.entreprendre.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OffersClientImpl implements IOffersClient {

    @Autowired
    private WebClient webClient;

    @Value("${offers.api.url}")
    private String offersApiUrl;

    @Override
    public Collection<Offer> getOffersFromEedkApi() throws ApiException, JsonMappingException, JsonProcessingException {
        try {
            List<Offer> offers = new ArrayList<Offer>();
            String response = webClient.get()
                .uri(offersApiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            jsonNode.forEach((item) -> {
                Offer offer = new Offer();

                offer.setId(Long.parseLong(item.get("id").textValue()));
                offer.setTitle(item.get("poste").textValue());
                offer.setCity(item.get("ville").textValue());
                offer.setReference(item.get("numint_offre").textValue());
                offer.setExpiresAt(LocalDate.parse(item.get("fin_candidature").textValue()));
                offer.setDescription(item.get("mission").textValue());
                offer.setBeginsAt(LocalDate.parse(item.get("debut_offre").textValue()));
                offer.setImageUrl(item.get("image").textValue());
                offer.setApplyUrl(item.get("postule").textValue());

                switch (item.get("contrat").textValue()) {
                    case "contrat de professionnalisation":
                        offer.setType(OfferTypeEnum.PROFESSIONAL_CONTRACT);
                        break;
                    case "Contrat d apprentissage ":
                        offer.setType(OfferTypeEnum.LEARNING_CONTRACT);
                        break;
                    default:
                        break;
                }

                JsonNode coordinates = item.get("geometry").get("coordinates");
                if (coordinates.isArray()) {
                    if (!coordinates.get(0).textValue().equals("")) {
                        offer.setLatitude(Double.parseDouble(coordinates.get(0).textValue()));
                    }
                    if (!coordinates.get(1).textValue().equals("")) {
                        offer.setLongitude(Double.parseDouble(coordinates.get(1).textValue()));
                    }
                }

                offers.add(offer);
            });

            return offers;

        } catch (WebClientException exception) {
            throw new ApiException("Please try again later because connection refused by eedk.fr server: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
