package com.yalday.proto.web.rest;

import com.yalday.proto.YaldayProtoApp;
import com.yalday.proto.repository.MerchantRepository;
import com.yalday.proto.service.mapper.MerchantMapper;
import com.yalday.proto.domain.Merchant;

import com.yalday.proto.domain.Resource;
import com.yalday.proto.domain.Booking;
import com.yalday.proto.repository.ResourceRepository;
import com.yalday.proto.repository.BookingRepository;
import com.yalday.proto.web.rest.errors.ExceptionTranslator;
import javax.inject.Inject;
import com.yalday.proto.service.dto.MerchantDTO;
import com.yalday.proto.service.MerchantService;
import org.springframework.test.util.ReflectionTestUtils;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResourceResource REST controller.
 *
 * @see ResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YaldayProtoApp.class)
public class ResourceResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Boolean DEFAULT_MULTIPLEBOOKING = false;
    private static final Boolean UPDATED_MULTIPLEBOOKING = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBB";





    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantService merchantService;

    @Inject
    private MerchantMapper merchantMapper;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restResourceMockMvc;

    private MockMvc restMerchantMockMvc;

    private Resource resource;

    private Merchant merchant;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResourceResource resourceResource = new ResourceResource(resourceRepository, merchantService);
        MerchantResource merchantResource = new MerchantResource();
        ReflectionTestUtils.setField(merchantResource, "merchantService", merchantService);
        this.restResourceMockMvc = MockMvcBuilders.standaloneSetup(resourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
        this.restMerchantMockMvc = MockMvcBuilders.standaloneSetup(merchantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resource createEntity() {
        Resource resource = new Resource()
            .text(DEFAULT_TEXT)
            .color(DEFAULT_COLOR)
            .capacity(DEFAULT_CAPACITY)
            .multiplebooking(DEFAULT_MULTIPLEBOOKING);
        return resource;
    }

    public static Merchant createMerchantEntity() {
        Merchant merchant = new Merchant()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .postcode(DEFAULT_POSTCODE)
            .country(DEFAULT_COUNTRY)
            .category(DEFAULT_CATEGORY)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR)
            .email(DEFAULT_EMAIL)
            .phonenumber(DEFAULT_PHONENUMBER)
            .userid(DEFAULT_USERID);
        //    .resources(Lists.newArrayList(createResource()));
        return merchant;
    }

    @Before
    public void initTest() {
        resourceRepository.deleteAll();
        merchantRepository.deleteAll();
        resource = createEntity();
        merchant = createMerchantEntity();
    }

    @Test
    public void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate + 1);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testResource.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testResource.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testResource.isMultiplebooking()).isEqualTo(DEFAULT_MULTIPLEBOOKING);
    }

    @Test
    public void createResourceForMerchant() throws Exception {
        initTest();
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        MerchantDTO merchantDTO = merchantMapper.merchantToMerchantDTO(merchant);


        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isCreated());

        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchants.get(merchants.size() - 1);

        restResourceMockMvc.perform(post ("/api/merchants/resource/{id}", testMerchant.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated())
            .andDo(print());


        MerchantDTO updatedMerchant = merchantService.findOne(testMerchant.getId());


        assertThat(updatedMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(updatedMerchant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(updatedMerchant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(updatedMerchant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(updatedMerchant.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(updatedMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(updatedMerchant.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(updatedMerchant.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(updatedMerchant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(updatedMerchant.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(updatedMerchant.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(updatedMerchant.getResources()).isNotNull();
    }


    @Test
    public void getResourcesForMerchant() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        MerchantDTO merchantDTO = merchantMapper.merchantToMerchantDTO(merchant);


        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isCreated());

        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchants.get(merchants.size() - 1);

        restResourceMockMvc.perform(post ("/api/merchants/resource/{id}", testMerchant.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated());

        MerchantDTO updatedMerchant = merchantService.findOne(testMerchant.getId());

        assertThat(updatedMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(updatedMerchant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(updatedMerchant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(updatedMerchant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(updatedMerchant.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(updatedMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(updatedMerchant.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(updatedMerchant.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(updatedMerchant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(updatedMerchant.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(updatedMerchant.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(updatedMerchant.getResources()).isNotNull();

        restResourceMockMvc.perform(get("/api/merchants/resource/{id}", testMerchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andDo(print());

    }

    @Test
    public void updateResourcesForMerchant() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        MerchantDTO merchantDTO = merchantMapper.merchantToMerchantDTO(merchant);

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isCreated());

        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchants.get(merchants.size() - 1);


        restResourceMockMvc.perform(post ("/api/merchants/resource/{id}", testMerchant.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated())
            .andDo(print());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeCreate + 1);
        Resource amendedResource = resources.get(resources.size() - 1);

        amendedResource
            .text(UPDATED_TEXT);

        MerchantDTO updatedMerchant = merchantService.findOne(testMerchant.getId());

        restResourceMockMvc.perform(put("/api/merchants/resource/{id}", updatedMerchant.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amendedResource)))
            .andExpect(status().isOk());

        List<Resource> resourceList = resourceRepository.findAll();
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    public void deleteResourceForMerchant() throws Exception {


        int databaseSizeBeforeCreate = 0;

        MerchantDTO merchantDTO = merchantMapper.merchantToMerchantDTO(merchant);

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isCreated());

        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchants.get(merchants.size() - 1);

        String merchantId = testMerchant.getId();

        restResourceMockMvc.perform(post ("/api/merchants/resource/{id}", testMerchant.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated())
            .andDo(print());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeCreate + 1);
        Resource resourceToBeDeleted = resources.get(resources.size() - 1);
        String resourceId = resourceToBeDeleted.getId();
        int i = resources.size();


        // Delete the Resource
        restResourceMockMvc.perform(delete("/api/merchants/resource/{id}", resourceToBeDeleted.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(i - 1);

    }






    @Test
    public void createResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource with an existing ID
        resource.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.save(resource);

        // Get all the resourceList
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].multiplebooking").value(hasItem(DEFAULT_MULTIPLEBOOKING.booleanValue())));
    }

    @Test
    public void getResource() throws Exception {
        // Initialize the database
        resourceRepository.save(resource);

        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", resource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resource.getId()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.multiplebooking").value(DEFAULT_MULTIPLEBOOKING.booleanValue()));
    }

    @Test
    public void getNonExistingResource() throws Exception {
        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateResource() throws Exception {
        // Initialize the database
        resourceRepository.save(resource);
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource
        Resource updatedResource = resourceRepository.findOne(resource.getId());
        updatedResource
            .text(UPDATED_TEXT)
            .color(UPDATED_COLOR)
            .capacity(UPDATED_CAPACITY)
            .multiplebooking(UPDATED_MULTIPLEBOOKING);

        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResource)))
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testResource.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testResource.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testResource.isMultiplebooking()).isEqualTo(UPDATED_MULTIPLEBOOKING);
    }

    @Test
    public void updateNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Create the Resource

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteResource() throws Exception {
        // Initialize the database
        resourceRepository.save(resource);
        int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Get the resource
        restResourceMockMvc.perform(delete("/api/resources/{id}", resource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resource.class);
        Resource resource1 = new Resource();
        resource1.setId("id1");
        Resource resource2 = new Resource();
        resource2.setId(resource1.getId());
        assertThat(resource1).isEqualTo(resource2);
        resource2.setId("id2");
        assertThat(resource1).isNotEqualTo(resource2);
        resource1.setId(null);
        assertThat(resource1).isNotEqualTo(resource2);
    }






}
