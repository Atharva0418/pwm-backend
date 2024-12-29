// package com.atharvadholakia.password_manager.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import com.atharvadholakia.password_manager.data.Credential;
// import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
// import com.atharvadholakia.password_manager.repository.CredentialRepository;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// public class CredentialServiceTests {

//   @Mock private CredentialRepository credentialRepository;

//   @InjectMocks private CredentialService credentialService;

//   @BeforeEach
//   public void setup() {
//     MockitoAnnotations.openMocks(this);
//   }

//   @Test
//   public void testCreateCredential() throws Exception {

//     Credential credential = new Credential("TestServiceName", "TestUsername", "TestP@ssword1");

//     when(credentialRepository.save(credential)).thenReturn(credential);

//     Credential result =
//         credentialService.createCredential(
//             "TestServiceName", "TestUsername", credential.getPassword());

//     assertEquals(credential.getServiceName(), result.getServiceName());
//     assertEquals(credential.getUsername(), result.getUsername());
//     assertEquals(credential.getPassword(), result.getPassword());

//     verify(credentialRepository).save(result);
//   }

//   @Test
//   public void testGetCredentialById() throws Exception {
//     Credential credential = new Credential("TestServiceName", "TestUsername", "TestPassword");
//     String id = credential.getCredentialId();
//     when(credentialRepository.findById(id)).thenReturn(Optional.of(credential));

//     Optional<Credential> result = credentialRepository.findById(id);

//     assertTrue(result.isPresent());
//     assertEquals(credential.getServiceName(), result.get().getServiceName());
//     assertEquals(credential.getUsername(), result.get().getUsername());
//     assertEquals(credential.getPassword(), result.get().getPassword());

//     verify(credentialRepository).findById(id);
//   }

//   @Test
//   public void testGetCredentialById_NotFound() throws Exception {
//     String id = "invalidId";
//     when(credentialRepository.findById(id)).thenReturn(Optional.empty());

//     assertThrows(ResourceNotFoundException.class, () -> credentialService.getCredentialById(id));

//     verify(credentialRepository, times(1)).findById(id);
//   }

//   // @Test
//   // public void testGetAllCredentials() throws Exception {
//   //   Credential credential1 = new Credential("TestServiceName1", "TestUsername1",
// "TestPassword1");
//   //   Credential credential2 = new Credential("TestServiceName2", "TestUsername2",
// "TestPassword2");

//   //   List<Credential> expectedCredentials = Arrays.asList(credential1, credential2);
//   //   when(credentialRepository.findAll()).thenReturn(expectedCredentials);

//   //   List<Credential> actualCredentials = credentialService.getAllCredentials();

//   //   assertEquals(expectedCredentials, actualCredentials);

//   //   verify(credentialRepository).findAll();
//   // }

//   // @Test
//   // public void testGetAllCredentials_EmptyList() throws Exception {
//   //   when(credentialRepository.findAll()).thenReturn(Collections.emptyList());

//   //   List<Credential> credential = credentialService.getAllCredentials();

//   //   assertTrue(credential.isEmpty());

//   //   verify(credentialRepository).findAll();
//   // }

//   @Test
//   public void testUpdateCredential() throws Exception {
//     String credentialID = "1234";
//     Credential existingCredential =
//         new Credential("TestServicename1", "TestUsername1", "TestPassword@1");
//     existingCredential.setCredentialId(credentialID);

//     existingCredential =
//         credentialService.updateCredential(
//             existingCredential, "UpdatedServiceName", "UpdatedUsername", "UpdatedPassword@1");

//     assertEquals(existingCredential.getServiceName(), "UpdatedServiceName");
//     assertEquals(existingCredential.getUsername(), "UpdatedUsername");
//     assertEquals(existingCredential.getPassword(), "UpdatedPassword@1");

//     verify(credentialRepository).save(existingCredential);
//   }

//   @Test
//   public void testDeleteCredentialByID() throws Exception {
//     credentialRepository.deleteById("ID");

//     verify(credentialRepository).deleteById("ID");
//   }
// }
