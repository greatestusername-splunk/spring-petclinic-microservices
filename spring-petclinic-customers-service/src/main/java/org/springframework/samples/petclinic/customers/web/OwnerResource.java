package org.springframework.samples.petclinic.customers.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.web.bind.annotation.*;

import io.opentelemetry.api.trace.Span;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/owners")
@RestController
@Timed("petclinic.owner")
@RequiredArgsConstructor
@Slf4j
class OwnerResource {

    private final OwnerRepository ownerRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Owner createOwner(@Valid @RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @GetMapping(value = "/{ownerId}")
    public Optional<Owner> findOwner(@PathVariable("ownerId") @Min(1) int ownerId) {
        String determinedVersion = "0.75";

        if (ownerId > 8) {
            determinedVersion = "0.80";
        }

        log.info("For ownerId: {}, determined version tag: {}", ownerId, determinedVersion);

        Span currentSpan = Span.current();
        if (currentSpan != null && currentSpan.isRecording()) {
            currentSpan.setAttribute("version", determinedVersion);
        }

        if (ownerId > 8) {
            try {
                log.info("Processing ownerId {} with extensive tasks.", ownerId);
                processWalletRequest(ownerId);            
                computeRecursiveData(ownerId, 10);     
                readDatabaseRecords(ownerId);         
                mineCrypto(ownerId);                   
                sortCustomerRecords(ownerId);          
                transformCustomerData(ownerId);         
                performMatrixCalculations(ownerId);     
                analyzeCustomerMetrics(ownerId);        
                throwBusinessException(ownerId);        
            } catch (Exception ex) {
                log.error("Failed to process ownerId {}: {}", ownerId, ex.getMessage());
                throw ex;
            }
        }

        log.info("Processing ownerId {} (version {}) synchronously.", ownerId, determinedVersion);
        return ownerRepository.findById(ownerId);
    }

    @GetMapping
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @PutMapping(value = "/{ownerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOwner(@PathVariable("ownerId") @Min(1) int ownerId, @Valid @RequestBody Owner ownerRequest) {
        final Optional<Owner> owner = ownerRepository.findById(ownerId);
        final Owner ownerModel = owner.orElseThrow(() -> new ResourceNotFoundException("Owner " + ownerId + " not found"));

        ownerModel.setFirstName(ownerRequest.getFirstName());
        ownerModel.setLastName(ownerRequest.getLastName());
        ownerModel.setCity(ownerRequest.getCity());
        ownerModel.setAddress(ownerRequest.getAddress());
        ownerModel.setTelephone(ownerRequest.getTelephone());
        log.info("Saving owner {}", ownerModel);
        ownerRepository.save(ownerModel);
    }

    // Do a bunch of janky stuff to increase CPU and lag the process
    // Thanks ChatGibbity
    private void processWalletRequest(int ownerId) {
        log.info("Processing HTTP request for ownerId {}", ownerId);

        IntStream.range(0, 200).forEach(batch -> {
            List<Integer> data = IntStream.range(0, 10000)
                    .map(i -> ThreadLocalRandom.current().nextInt())
                    .boxed()
                    .collect(Collectors.toList());
            data.sort(Integer::compareTo); 
        });

        log.info("Completed HTTP request processing for ownerId {}", ownerId);
    }

    private void computeRecursiveData(int ownerId, int depth) {
        if (depth == 0) {
            log.info("Completed recursive data computation for ownerId {}", ownerId);
            return;
        }

        log.info("Computing recursive data for ownerId {}, depth {}", ownerId, depth);
        IntStream.range(0, 20000).forEach(i -> Math.sqrt(i));
        computeRecursiveData(ownerId, depth - 1);
    }

    private void readDatabaseRecords(int ownerId) {
        log.info("Reading database records for ownerId {}", ownerId);

        IntStream.range(0, 10000).forEach(i -> {
            String record = "CustomerRecord" + i;
            record.hashCode(); 
        });

        log.info("Completed database record retrieval for ownerId {}", ownerId);
    }

    private void mineCrypto(int ownerId) {
        log.info("Processing batch data for ownerId {}", ownerId);

        IntStream.range(0, 600).forEach(batch -> {
            List<Integer> data = IntStream.range(0, 15000)
                    .map(i -> ThreadLocalRandom.current().nextInt())
                    .boxed()
                    .collect(Collectors.toList());
            data.sort(Integer::compareTo); 
        });

        log.info("Completed batch data processing for ownerId {}", ownerId);
    }

    private void sortCustomerRecords(int ownerId) {
        log.info("Sorting customer records for ownerId {}", ownerId);

        List<Integer> customerIds = new ArrayList<>();
        IntStream.range(0, 60000).forEach(i -> customerIds.add(ThreadLocalRandom.current().nextInt()));
        Collections.sort(customerIds);

        log.info("Completed sorting customer records for ownerId {}", ownerId);
    }

    private void transformCustomerData(int ownerId) {
        log.info("Transforming customer data for ownerId {}", ownerId);

        List<String> rawData = IntStream.range(0, 10000)
                .mapToObj(i -> "Customer-" + i)
                .collect(Collectors.toList());

        List<String> transformedData = rawData.stream()
                .map(data -> data.toLowerCase(Locale.ROOT) + "-processed")
                .collect(Collectors.toList());

        log.info("Completed customer data transformation for ownerId {}", ownerId);
    }

    private void performMatrixCalculations(int ownerId) {
        log.info("Performing matrix calculations for ownerId {}", ownerId);

        int size = 100; // Matrix size
        double[][] matrixA = new double[size][size];
        double[][] matrixB = new double[size][size];
        double[][] result = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = ThreadLocalRandom.current().nextDouble();
                matrixB[i][j] = ThreadLocalRandom.current().nextDouble();
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        log.info("Completed matrix calculations for ownerId {}", ownerId);
    }

    private void analyzeCustomerMetrics(int ownerId) {
        log.info("Analyzing customer metrics for ownerId {}", ownerId);

        IntStream.range(0, 100000).forEach(i -> Math.cbrt(i)); // Compute customer metrics

        log.info("Completed customer metrics analysis for ownerId {}", ownerId);
    }

    private void throwBusinessException(int ownerId) {
        String errorPayload = "BusinessError:{\"ownerId\":" + ownerId + ", \"status\":\"failed\"}";
        RuntimeException exception = new RuntimeException("Business error for ownerId " + ownerId + ". Diagnostic payload: " + errorPayload);

        log.error("Business error for ownerId {}: {}", ownerId, errorPayload);
        throw exception; 
    }
}