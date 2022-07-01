package com.yoonbin.triple.club.mileage.point.controller;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.domain.PointDto;
import com.yoonbin.triple.club.mileage.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/events")
    public PointDto savePoint(@RequestBody PointDto pointDto){
        if(pointDto.getAction().equals("ADD")){
            pointService.addPoint(pointDto);
        }else if(pointDto.getAction().equals("MOD")){
            pointService.modPoint(pointDto);
        }else if(pointDto.getAction().equals("DELETE")){
            pointService.deletePoint(pointDto);
        }else {
            System.out.println("정확한 Action이 아닙니다.");
        }
        return pointDto;
    }

    @GetMapping("/points")
    public List<Point> getPoints(){
        return pointService.getPoints();
    }

}
