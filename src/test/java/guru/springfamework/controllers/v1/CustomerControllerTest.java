package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetListOfCustomers() throws Exception {

        // given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Kento");
        customer1.setLastname("Takano");
        customer1.setCustomerUrl("/api/v1/customers/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("Yui");
        customer2.setLastname("Aragaki");
        customer2.setCustomerUrl("/api/v1/customers/2");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Kento");
        customer1.setLastname("Takano");
        customer1.setCustomerUrl("/api/v1/customers/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        // when
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Kento")))
                .andExpect(jsonPath("$.lastname", equalTo("Takano")));
    }
}
