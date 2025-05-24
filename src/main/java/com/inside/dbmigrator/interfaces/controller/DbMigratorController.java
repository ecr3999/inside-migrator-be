package com.inside.dbmigrator.interfaces.controller;

import com.inside.dbmigrator.service.DbMigratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/db-migrator")
public class DbMigratorController {
    private final DbMigratorService dbMigratorService;

    @PostMapping("/migrate-revenue")
    public ResponseEntity<String> migrateRevenue(
            @RequestParam(required = false) Integer startId,
            @RequestParam(required = false) Integer endId,
            @RequestParam(required = false) Integer month
    ) {
        try {
            dbMigratorService.migrateRevenue(startId, endId, month);
            return ResponseEntity.ok("Migration completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/migrate-traffic")
    public ResponseEntity<String> migrateTraffic(
            @RequestParam(required = false) Integer startId,
            @RequestParam(required = false) Integer endId,
            @RequestParam(required = false) Integer month
    ) {
        try {
            dbMigratorService.migrateTraffic(startId, endId, month);
            return ResponseEntity.ok("Migration completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

//    @Operation(summary = "Migrate to DB")
//    @PostMapping("/migrate-rbt")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

//        try {
//            // Save to a temp file
//            Path tempFile = Files.createTempFile("rbt-", ".csv");
//            file.transferTo(tempFile.toFile());
//
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("executedAt", System.currentTimeMillis())
//                    .addString("filePath", tempFile.toAbsolutePath().toString())
//                    .toJobParameters();
//
//            jobLauncher.run(importRbtJob, jobParameters);
//            return ResponseEntity.ok("Batch job started with uploaded CSV file.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
//        }
//    }
//}
    }
}
