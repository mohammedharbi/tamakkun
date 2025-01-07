package com.example.tamakkun.ServiceTest;

import com.example.tamakkun.Controller.AuthController;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.ParentRepository;
import com.example.tamakkun.Service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)


public class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    AuthRepository authRepository;

    @Mock
    CentreRepository centreRepository;

    @Mock
    ParentRepository parentRepository;

    MyUser myUser;
    Centre centre;
    Parent parent;
    List<MyUser> userList;
    List<Centre> unverifiedCentres;
    List<Parent> unActiveParents;

    @BeforeEach
    void setUp() {
        myUser = new MyUser();
        centre = new Centre();
        centre.setId(1);
        centre.setCommercialLicense("valid-license");
        centre.setIsVerified(false);
        parent = new Parent();
        parent.setId(1);
        parent.setIsActive(true);

        userList = new ArrayList<>();
        userList.add(myUser);

        unverifiedCentres = new ArrayList<>();
        unverifiedCentres.add(centre);

        unActiveParents = new ArrayList<>();
        unActiveParents.add(parent);
    }

    @Test
    public void getAllUsersTest() {
        when(authRepository.findAll()).thenReturn(userList);

        List<MyUser> users = authService.getAllUsers();

        Assertions.assertEquals(users, userList);
        verify(authRepository, times(1)).findAll();
    }

    @Test
    public void verifyCentreTest() {
        when(centreRepository.findCentreById(centre.getId())).thenReturn(centre);

        authService.verifyCentre(centre.getId());

        Assertions.assertTrue(centre.getIsVerified());
        verify(centreRepository, times(1)).findCentreById(centre.getId());
        verify(centreRepository, times(1)).save(centre);
    }

    @Test
    public void getAllUnverifiedCentresTest() {
        when(centreRepository.findUnverifiedCentres()).thenReturn(unverifiedCentres);

        List<Centre> centres = authService.getAllUnverifiedCentres();

        Assertions.assertEquals(centres, unverifiedCentres);
        verify(centreRepository, times(1)).findUnverifiedCentres();
    }

    @Test
    public void unActiveParentTest() {
        when(parentRepository.findParentById(parent.getId())).thenReturn(parent);

        authService.unActiveParent(parent.getId());

        Assertions.assertFalse(parent.getIsActive());
        verify(parentRepository, times(1)).findParentById(parent.getId());
    }

    @Test
    public void getAllUnActiveParentTest() {
        parent.setIsActive(false);
        when(parentRepository.getParentByIsActive(false)).thenReturn(unActiveParents);

        List<Parent> parents = authService.getAllUnActiveParent();

        Assertions.assertEquals(parents, unActiveParents);
        verify(parentRepository, times(1)).getParentByIsActive(false);
    }


}
