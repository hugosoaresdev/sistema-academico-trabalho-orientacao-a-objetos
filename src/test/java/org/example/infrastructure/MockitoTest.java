package org.example.infrastructure;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MockitoTest {

    @Test
    void shouldUseMockitoMock() {

        List<String> list = mock(List.class);

        when(list.size()).thenReturn(5);

        assertEquals(5, list.size());

        verify(list).size();
    }
}