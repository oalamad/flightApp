package com.islamicbank.fa.api.controller;

import com.islamicbank.fa.api.model.request.CommentUpSrtRequest;
import com.islamicbank.fa.api.model.request.CreateCityRequest;
import com.islamicbank.fa.api.model.request.SearchAirportRequest;
import com.islamicbank.fa.api.model.request.SearchCityRequest;
import com.islamicbank.fa.api.model.response.AirportResponse;
import com.islamicbank.fa.api.model.response.CityResponse;
import com.islamicbank.fa.api.model.response.CommentResponse;
import com.islamicbank.fa.api.model.response.TripResponse;
import com.islamicbank.fa.repository.entity.User;
import com.islamicbank.fa.service.CityMgmtService;
import com.islamicbank.fa.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * City controller used to handle cities API functions.
 *
 * @author Omar Ismail
 * @version 1.0
 */
@Log4j2
@Tag(name = "City Management",
        description = "A set of authorized APIs, for getting and managing system cities.")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {

    private final CityMgmtService cityMgmtService;
    private final TravelService travelService;

    @Operation(summary = "Get all cities.",
            description = "Get city or all cities. You can limit the # of returned comments.")
    @GetMapping
    public List<CityResponse> getAllCities(@RequestParam(defaultValue = "0")
                                           @Min(0) @Max(Integer.MAX_VALUE) int cLimit) {
        return this.cityMgmtService.searchCities(new SearchCityRequest(""), cLimit);
    }

    @Operation(summary = "Search all cities by name.",
            description = "Find city or all cities by name. You can limit the # of returned comments.")
    @PostMapping("search")
    public List<CityResponse> searchCities(@RequestParam(defaultValue = "0")
                                           @Min(0) @Max(Integer.MAX_VALUE) int cLimit,
                                           @RequestBody @Valid SearchCityRequest request) {
        return this.cityMgmtService.searchCities(request, cLimit);
    }


    @Operation(summary = "Add a new city.",
            description = """
                    Add a new city to the system.
                    - Note that you can add country to city either:
                      1. by its id if it is already exist in the system, or
                      2. by country name if not exist, then the system will \
                         creat the country and attach it to the city.
                    """)
    @PostMapping
    public CityResponse createCity(@RequestBody @Valid CreateCityRequest request) {
        return this.cityMgmtService.addCity(request);
    }


    @Operation(summary = "Exited! and wanna travel.",
            description = "Find the cheapest trip from source country to a destination country.")
    @GetMapping("travel")
    public List<TripResponse> travel(@RequestParam @Size(min = 3) String from,
                                     @RequestParam @Size(min = 3) String to) {

        if (from.isBlank() || to.isBlank())
            throw new IllegalArgumentException("""
                    Neither source nor destination airport codes can be empty! \
                    Please try with other valid values.
                    """);

        if (from.trim().equals(to.trim()))
            throw new IllegalArgumentException(String.format(
                    "You are traveling from and to the same destination [%s]", to));

        return this.travelService
                .travel(from.trim().toUpperCase(), to.trim().toUpperCase());
    }

    /*
      Airport Management
   */
    @Operation(summary = "Find city airports.",
            description = "Find all airports for a specific city.")
    @PostMapping("{id}/airports")
    public List<AirportResponse> searchAirports(@Parameter(description = "City Id")
                                                @PathVariable(name = "id")
                                                @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                                                @RequestBody @Valid SearchAirportRequest request) {
        return this.cityMgmtService.searchAirports(request, cityId);
    }

    @Operation(summary = "Get all airports by a any name.",
            description = "Find all airports by airport, city or country name.")
    @GetMapping("/airports")
    public List<AirportResponse> searchForCityOrCountryAirports(
            @Parameter(description = "Airport, city or country name")
            @RequestParam @Size(min = 1) String name) {
        return this.travelService.findAirportsForCityOrCountry(name);
    }
     
    /*
       Comments Management
    */

    //Add comment
    @Operation(summary = "Add a city comment.",
            description = "Wanna add a comment to a city you have visited.")
    @PostMapping("{id}/comments")
    public CommentResponse addComment(@Parameter(description = "City Id")
                                      @PathVariable(name = "id")
                                      @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                                      @RequestBody @Valid CommentUpSrtRequest request) {
        return this.cityMgmtService.addComment(getCurrentLoginUser(), cityId, request);
    }

    //update comment
    @Operation(summary = "Update my city comment.",
            description = "Wanna change your comment to a city you have visited.")
    @PutMapping("{id}/comments/{cid}")
    public void updateComment(@Parameter(description = "City Id")
                              @PathVariable(name = "id")
                              @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                              @Parameter(description = "Comment Id")
                              @PathVariable("cid")
                              @Min(1) @Max(Integer.MAX_VALUE) int commentId,
                              @RequestBody @Valid CommentUpSrtRequest request) {

        this.cityMgmtService.updateComment(getCurrentLoginUser(), cityId, commentId, request);
    }

    //Delete comment
    @Operation(summary = "Delete my city comment.",
            description = "Changed your mind, don't like your comment then delete it.")
    @DeleteMapping("{id}/comments/{cid}")
    public void deleteComment(@Parameter(description = "City Id") @PathVariable(name = "id")
                              @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                              @Parameter(description = "Comment Id") @PathVariable("cid")
                              @Min(1) @Max(Integer.MAX_VALUE) int commentId) {

        this.cityMgmtService.deleteComment(getCurrentLoginUser(), cityId, commentId);
    }

    private User getCurrentLoginUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
