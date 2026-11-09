package com.example.bookmanager.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    private CacheInspectorService cacheInspectorService;

    @GetMapping("/names")
    public Map<String, Long> getCacheNamesAndSizes() {
        return cacheInspectorService.getAllCacheNamesAndSizes();
    }

    @GetMapping("/{cacheName}")
    public Map<Object, Object> inspectCache(@PathVariable String cacheName) {
        return cacheInspectorService.inspectCache(cacheName);
    }

    @GetMapping("/stats/{cacheName}")
    public Map<String, Object> statCache(@PathVariable String cacheName) {
        return cacheInspectorService.getCacheStats(cacheName);
    }
}
