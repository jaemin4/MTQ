package project.mqtt.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specification")
@Tag(name = "API 명세서", description = "API 명세서 초기 구성")
@Slf4j
public class ApiSwaggerController {

    @GetMapping("/convention")
    @Operation(
            summary = "Convention",
            description = """
            Convention
        **Request Parameters**
        
        """
    )
    public void ApiConvention(

    ) {

    }






}
