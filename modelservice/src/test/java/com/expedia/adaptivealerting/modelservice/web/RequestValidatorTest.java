package com.expedia.adaptivealerting.modelservice.web;


import com.expedia.adaptivealerting.modelservice.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class RequestValidatorTest {

    @Mock
    private User user;

    /* Class under test */
    @InjectMocks
    private RequestValidator requestValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidateUser_successful() {
        User user = new User("test-user");
        requestValidator.validateUser(user);
    }

    @Test(expected=RuntimeException.class)
    public void testValidateUser_idempty() throws IllegalArgumentException  {
        User user = new User("");
        requestValidator.validateUser(user);
    }

    @Test(expected=RuntimeException.class)
    public void testValidateUser_idcontainswhitespaces() throws IllegalArgumentException {
        User user = new User("test user");
        requestValidator.validateUser(user);
    }

    @Test
    public void testValidateExpression_successful() {
        Expression expression = new Expression();
        expression.setOperator(Operator.AND);
        List<Operand> operandsList = new ArrayList<>();
        Operand operand1 = new Operand();
        Operand operand2 = new Operand();
        Operand operand3 = new Operand();
        Field field1 = new Field();
        field1.setKey("name");
        field1.setValue("sample-app");
        Field field2 = new Field();
        field2.setKey("env");
        field2.setValue("prod");
        Field field3 = new Field();
        field3.setKey("type");
        field3.setValue("gauge");
        operand1.setField(field1);
        operand2.setField(field2);
        operand3.setField(field3);
        operandsList.add(operand1);
        operandsList.add(operand2);
        expression.setOperands(operandsList);
        requestValidator.validateExpression(expression);
    }

    @Test
    public void testValidateDetector_successful() {
        Detector detector = new Detector(UUID.fromString("aeb4d849-847a-45c0-8312-dc0fcf22b639"));
        requestValidator.validateDetector(detector);
    }

    @Test(expected=RuntimeException.class)
    public void testValidateOperand_keyempty() throws IllegalArgumentException {
        Expression expression = new Expression();
        expression.setOperator(Operator.AND);
        List<Operand> operandsList = new ArrayList<>();
        Operand testoperand = new Operand();
        testoperand.setField(new Field("", "sample-app"));
        operandsList.add(testoperand);
        expression.setOperands(operandsList);
        requestValidator.validateExpression(expression);
    }

    @Test(expected=RuntimeException.class)
    public void testValidateOperand_valueempty() throws IllegalArgumentException {
        Expression expression = new Expression();
        expression.setOperator(Operator.AND);
        List<Operand> operandsList = new ArrayList<>();
        Operand testoperand = new Operand();
        testoperand.setField(new Field("name", ""));
        operandsList.add(testoperand);
        expression.setOperands(operandsList);
        requestValidator.validateExpression(expression);
    }

    @Test(expected=RuntimeException.class)
    public void testValidateOperand_keystartswithDetectorMappingEntityAAPREFIX() throws IllegalArgumentException  {
        Expression expression = new Expression();
        expression.setOperator(Operator.AND);
        List<Operand> operandsList = new ArrayList<>();
        Operand testOperand = new Operand();
        testOperand.setField(new Field("aa_name", "sample-app"));
        operandsList.add(testOperand);
        expression.setOperands(operandsList);
        requestValidator.validateExpression(expression);
    }

}