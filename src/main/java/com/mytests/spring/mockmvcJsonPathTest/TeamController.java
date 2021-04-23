package com.mytests.spring.mockmvcJsonPathTest;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @RequestMapping("/team/people")
    public MultiValueMap<String, Person> get() {
        MultiValueMap<String, Person> map = new LinkedMultiValueMap<>();

        map.add("developers", new Person("vasya"));
        map.add("developers", new Person("valya"));
        map.add("developers", new Person("masha"));
        map.add("developers", new Person("pasha"));
        map.add("developers", new Person("katya"));

        map.add("testers", new Person("vova"));
        map.add("testers", new Person("dasha"));
        map.add("support", new Person("vanya"));
        map.add("manager", new Person("anya"));

        return map;
    }
}