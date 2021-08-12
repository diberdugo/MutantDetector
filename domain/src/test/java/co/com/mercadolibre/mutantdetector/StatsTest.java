package co.com.mercadolibre.mutantdetector;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.service.MutantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatsTest {

    @InjectMocks
    private MutantServiceImpl mutantServicePort;

    @Mock
    private MutantPersistencePort mutantPersistencePort;

    @Test
    public void getStats() {
        MutantStatsDTO expectedMutantStatsDTO = MutantStatsDTO.builder()
                .humans(50)
                .mutants(10)
                .build();

        when(mutantPersistencePort.getStats()).thenReturn(expectedMutantStatsDTO);

        MutantStatsDTO mutantStatsDTO = mutantServicePort.getStats();

        assertEquals(expectedMutantStatsDTO.getHumans(), mutantStatsDTO.getHumans());
        assertEquals(expectedMutantStatsDTO.getMutants(), mutantStatsDTO.getMutants());
        assertEquals(expectedMutantStatsDTO.getRatio(), mutantStatsDTO.getRatio());
    }

    @Test
    public void getStatsWithoutHumans() {
        MutantStatsDTO expectedMutantStatsDTO = MutantStatsDTO.builder()
                .humans(0)
                .mutants(2)
                .build();

        when(mutantPersistencePort.getStats()).thenReturn(expectedMutantStatsDTO);

        MutantStatsDTO mutantStatsDTO = mutantServicePort.getStats();

        assertEquals(expectedMutantStatsDTO.getHumans(), mutantStatsDTO.getHumans());
        assertEquals(expectedMutantStatsDTO.getMutants(), mutantStatsDTO.getMutants());
        assertEquals(0, mutantStatsDTO.getRatio().longValue());
    }

    @Test
    public void getStatsWithoutMutants() {
        MutantStatsDTO expectedMutantStatsDTO = MutantStatsDTO.builder()
                .humans(2)
                .mutants(0)
                .build();

        when(mutantPersistencePort.getStats()).thenReturn(expectedMutantStatsDTO);

        MutantStatsDTO mutantStatsDTO = mutantServicePort.getStats();

        assertEquals(expectedMutantStatsDTO.getHumans(), mutantStatsDTO.getHumans());
        assertEquals(expectedMutantStatsDTO.getMutants(), mutantStatsDTO.getMutants());
        assertEquals(0, mutantStatsDTO.getRatio().longValue());
    }
}
