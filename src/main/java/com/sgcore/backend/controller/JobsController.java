package com.sgcore.backend.controller;

import com.sgcore.backend.model.Job;
import com.sgcore.backend.repository.JobRepository;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobsController {

    private final JobRepository jobRepository;

    public JobsController(
            JobRepository jobRepository
    ) {

        this.jobRepository =
                jobRepository;
    }

    // =========================
    // GET ALL JOBS
    // =========================

    @GetMapping
    public List<Job> listJobs() {

        return jobRepository.findAll();
    }

    // =========================
    // CREATE JOB
    // =========================

    @PostMapping
    public Job createJob(

            @RequestBody Job job

    ) {

        job.setId(null);

        // =========================
        // CUSTOM JOB CODE
        // =========================

        String code =
                "JOB-" +
                System.currentTimeMillis();

        job.setJobCode(code);

        return jobRepository.save(job);
    }

    // =========================
    // GET JOB BY MONGO ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(

            @PathVariable String id

    ) {

        Optional<Job> job =
                jobRepository.findById(id);

        return job
                .map(ResponseEntity::ok)

                .orElseGet(() ->
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // =========================
    // GET JOB BY JOB CODE
    // =========================

    @GetMapping("/code/{jobCode}")
    public ResponseEntity<Job> getJobByCode(

            @PathVariable String jobCode

    ) {

        Optional<Job> job =

                jobRepository
                        .findByJobCode(
                                jobCode
                        );

        return job
                .map(ResponseEntity::ok)

                .orElseGet(() ->

                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // =========================
    // UPDATE JOB
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(

            @PathVariable String id,

            @RequestBody Job updated

    ) {

        return jobRepository
                .findById(id)

                .map(j -> {

                    j.setTitle(
                            updated.getTitle()
                    );

                    j.setDescription(
                            updated.getDescription()
                    );

                    j.setLocation(
                            updated.getLocation()
                    );

                    j.setType(
                            updated.getType()
                    );

                    return ResponseEntity.ok(
                            jobRepository.save(j)
                    );

                })

                .orElseGet(() ->

                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // =========================
    // DELETE JOB
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(

            @PathVariable String id

    ) {

        if (
                !jobRepository.existsById(id)
        ) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        jobRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}