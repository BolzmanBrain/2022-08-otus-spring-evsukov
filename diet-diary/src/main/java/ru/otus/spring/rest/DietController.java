package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.DietDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.mapper.DietMapperService;
import ru.otus.spring.service.DietService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class DietController {
    private final DietService dietService;
    private final DietMapperService dietMapperService;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/api/v1/diet/{id}")
    public DietDto getDiet(@PathVariable Long id) {
        return dietService.findById(id)
                .map(dietMapperService::toDto).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/api/v1/diet")
    public DietDto getDiet(@RequestParam("date") String dateString, @RequestParam("userId") Long userId) throws ParseException {
        var date = dateFormatter.parse(dateString);

        return dietService.findByDateAndUserId(date, userId)
                .map(dietMapperService::toDto).orElse(null);
    }

    @PostMapping("/api/v1/diet")
    public void postDiet(@RequestBody DietDto dietDto) {
        System.out.println(dietDto);
        var diet = dietMapperService.toDomain(dietDto);
        dietService.save(diet);
    }

    @PutMapping("/api/v1/diet")
    public void putDiet(@RequestBody DietDto dietDto) {
        var diet = dietMapperService.toDomain(dietDto);
        System.out.println("putDiet");
        System.out.println(diet);
        dietService.save(diet);
    }

    @DeleteMapping("/api/v1/diet/{id}")
    public void deleteDiet(@PathVariable Long id) {
        dietService.deleteById(id);
    }
}
