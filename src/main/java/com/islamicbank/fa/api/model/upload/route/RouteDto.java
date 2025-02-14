package com.islamicbank.fa.api.model.upload.route;

import com.islamicbank.fa.api.model.upload.converter.ConvertUnwantedStringsToDefault;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import lombok.Data;

/**
 * @author Omar Ismail
 * @version 1.0
 **/

@Data
public class RouteDto {
    
    @CsvBindByPosition(position = 0)
    private String airlineCode;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "0")
    @CsvBindByPosition(position = 1)
    private Integer airlineId;
    
    @CsvBindByPosition(position = 2)
    private String srcAirportCode;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "0")
    @CsvBindByPosition(position = 3)
    private Integer srcAirportId;
    
    @CsvBindByPosition(position = 4)
    private String destAirportCode;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "0")
    @CsvBindByPosition(position = 5)
    private Integer destAirportId;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "N")
    @CsvBindByPosition(position = 6)
    private String codeShare;
    
    @CsvBindByPosition(position = 7)
    private Integer stops;
    
    @CsvBindByPosition(position = 8)
    private String equipment;
    
    @CsvBindByPosition(position = 9)
    private double price;
}