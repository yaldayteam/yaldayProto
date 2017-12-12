package com.yalday.proto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yalday.proto.domain.Resource;
import com.yalday.proto.service.MerchantService;
import com.yalday.proto.service.dto.MerchantDTO;

import com.yalday.proto.repository.ResourceRepository;
import com.yalday.proto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Resource.
 */
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final Logger log = LoggerFactory.getLogger(ResourceResource.class);

    private static final String ENTITY_NAME = "resource";

    private final ResourceRepository resourceRepository;

    private final MerchantService merchantService;

    public ResourceResource(ResourceRepository resourceRepository, MerchantService merchantService) {
        this.resourceRepository = resourceRepository;
        this.merchantService = merchantService;
    }

    /**
     * POST  /resources : Create a new resource.
     *
     * @param resource the resource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resource, or with status 400 (Bad Request) if the resource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resources")
    @Timed
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) throws URISyntaxException {
        log.debug("REST request to save Resource : {}", resource);
        if (resource.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new resource cannot already have an ID")).body(null);
        }
        Resource result = resourceRepository.save(resource);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /merchants/resource/{id} : Create a new resource for merchant with id.
     *
     * @param the resource to create & the merchant id to add the resource to
     * @return the ResponseEntity with status 201 (Created) and with body the new resource, or with status 400 (Bad Request) if the resource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchants/resource/{id}")
    @Timed
    public ResponseEntity<Resource> createResourceForMerchant(@RequestBody Resource resource, @PathVariable String id) throws URISyntaxException {
        log.debug("REST request to save Resource to Merchant : {}", resource, id);
        if (resource.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resource", "idexists", "A new resource cannot already have an ID")).body(null);
        }

        //Step one save resource - now result will have id
        Resource resultResource = resourceRepository.save(resource);

        //Step two - retrieve merchant with id equal to param id
        MerchantDTO resultMerchantDTO = new MerchantDTO();
        resultMerchantDTO = merchantService.findOne(id);

        //Step three - add resource to merchant returned
        resultMerchantDTO.addResource(resultResource);

        //Step four - save merchant - so now merchant with nested resource has been saved and resource also saved
        merchantService.save(resultMerchantDTO);


        return ResponseEntity.created(new URI("/api/merchants/resource/" + resultResource.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, resultResource.getId().toString()))
            .body(resultResource);

    }

    /**
     * GET  /merchants/resource/{id}: Get all resources for merchant with id.
     *
     * @param the merchant id to retrieve all resource for
     * @return the ResponseEntity with status 200 (ok) and the list of resources in the body
     */
    @GetMapping("/merchants/resource/{id}")
    @Timed
    public List<Resource> GetAllResourcesForMerchant(@PathVariable String id) {
        log.debug("REST request to GetAllResourcesForMerchant with id : {}", id);

        //find the merchant by the Merchant id passed in
        MerchantDTO merchantResult = merchantService.findOne(id);

        //get the resources for that Merchant
        List<Resource> resources = merchantResult.getResources();

        return resources;
    }

    /**
     * PUT  /merchants/resource/{id} : Updates an existing resource for merchant with id.
     *
     * @param the resource to update & the merchant id to update the resource for
     * @return the ResponseEntity with status 200 (OK) and with body the updated resource,
     * or with status 400(Bad Request) if the resource is not valid,
     * or with status 500 (Internal Server Error) if the resource could not be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchants/resource/{id}")
    @Timed
    public ResponseEntity<Resource> updatedResourceForMerchant(@RequestBody Resource resource, @PathVariable String id) throws URISyntaxException {
        log.debug("REST request to update Resource for Merchant : {}", resource, id);

        if (resource.getId() == null) {
            return createResourceForMerchant(resource, id);
        }

        //saves resource to resource collection seperately in accordance with REST
        Resource resultResource = resourceRepository.save(resource);

        //Step two - retrieve merchant with id equal to param id
        MerchantDTO resultMerchantDTO = new MerchantDTO();
        resultMerchantDTO = merchantService.findOne(id);

        //Step three - update resource for merchant returned
        resultMerchantDTO.updateResource(resultResource);


        //Step four - save merchants - now merchant with updated resource has been saved and updated resource also saved
        merchantService.save(resultMerchantDTO);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resource.getId().toString()))
            .body(resultResource);
    }


    /**
     * DELETE  /merchants/resource/{id} : delete the resource for merchant with id
     *
     * @param the resource to be deleted and the merchant id to delete the resource from
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchants/resource/{id}")
    @Timed
    public ResponseEntity<Void> deleteResourceForMerchant(@PathVariable String id) {

        log.debug("REST request to delete Resource for Resouce id : {}");

        //Step one - delete the resource from booking collection in accordance with REST
        resourceRepository.delete(id);

        //Step two - retrieve merchant with id equal to param id
        MerchantDTO resultMerchantDTO = new MerchantDTO();
        resultMerchantDTO = merchantService.findOneByResourceId(id);

        //Step three - delete resource for merchant returned
        resultMerchantDTO.deleteResource(id);

        //Step four - save merchants - now merchant with deleted resource has been saved and deleted resource also
        merchantService.save(resultMerchantDTO);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * PUT  /resources : Updates an existing resource.
     *
     * @param resource the resource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resource,
     * or with status 400 (Bad Request) if the resource is not valid,
     * or with status 500 (Internal Server Error) if the resource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resources")
    @Timed
    public ResponseEntity<Resource> updateResource(@RequestBody Resource resource) throws URISyntaxException {
        log.debug("REST request to update Resource : {}", resource);
        if (resource.getId() == null) {
            return createResource(resource);
        }
        Resource result = resourceRepository.save(resource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resources : get all the resources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resources in body
     */
    @GetMapping("/resources")
    @Timed
    public List<Resource> getAllResources() {
        log.debug("REST request to get all Resources");
        return resourceRepository.findAll();
    }

    /**
     * GET  /resources/:id : get the "id" resource.
     *
     * @param id the id of the resource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resource, or with status 404 (Not Found)
     */
    @GetMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Resource> getResource(@PathVariable String id) {
        log.debug("REST request to get Resource : {}", id);
        Resource resource = resourceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resource));
    }

    /**
     * DELETE  /resources/:id : delete the "id" resource.
     *
     * @param id the id of the resource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteResource(@PathVariable String id) {
        log.debug("REST request to delete Resource : {}", id);
        resourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
