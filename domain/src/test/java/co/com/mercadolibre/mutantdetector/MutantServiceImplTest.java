package co.com.mercadolibre.mutantdetector;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.MutantException;
import co.com.mercadolibre.mutantdetector.ports.spi.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.service.MutantServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MutantServiceImplTest {

    @InjectMocks
    private MutantServiceImpl mutantServicePort;

    @Mock
    private MutantPersistencePort mutantPersistencePort;

    @Test
    public void isMutant() throws MutantException {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutantObliqueSequence() throws MutantException {
        String[] dna = {"ACGTCA", "GGTGAC", "TTTATT", "GCACCT", "TCACCC", "AGGGGT"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isHuman() throws MutantException {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, false);

        Assert.assertFalse("The dna provided must belong to a human",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void getStatus() {
        MutantStatsDTO expectedMutantStatsDTO = MutantStatsDTO.builder()
                .humans(50)
                .mutants(10)
                .build();

        when(mutantPersistencePort.getStatus()).thenReturn(expectedMutantStatsDTO);

        MutantStatsDTO mutantStatsDTO = mutantServicePort.getStatus();

        Assert.assertEquals(expectedMutantStatsDTO.getHumans(), mutantStatsDTO.getHumans());
        Assert.assertEquals(expectedMutantStatsDTO.getMutants(), mutantStatsDTO.getMutants());
        Assert.assertEquals(expectedMutantStatsDTO.getRatio(), mutantStatsDTO.getRatio());
    }

    @Test
    public void getStatusWithoutHumans() {
        MutantStatsDTO expectedMutantStatsDTO = MutantStatsDTO.builder()
                .humans(0)
                .mutants(2)
                .build();

        when(mutantPersistencePort.getStatus()).thenReturn(expectedMutantStatsDTO);

        MutantStatsDTO mutantStatsDTO = mutantServicePort.getStatus();

        Assert.assertEquals(expectedMutantStatsDTO.getHumans(), mutantStatsDTO.getHumans());
        Assert.assertEquals(expectedMutantStatsDTO.getMutants(), mutantStatsDTO.getMutants());
        Assert.assertEquals(0, mutantStatsDTO.getRatio().longValue());
    }

    @Test
    public void getStatusWithoutMutants() {
        MutantStatsDTO expectedMutantStatsDTO = MutantStatsDTO.builder()
                .humans(2)
                .mutants(0)
                .build();

        when(mutantPersistencePort.getStatus()).thenReturn(expectedMutantStatsDTO);

        MutantStatsDTO mutantStatsDTO = mutantServicePort.getStatus();

        Assert.assertEquals(expectedMutantStatsDTO.getHumans(), mutantStatsDTO.getHumans());
        Assert.assertEquals(expectedMutantStatsDTO.getMutants(), mutantStatsDTO.getMutants());
        Assert.assertEquals(0, mutantStatsDTO.getRatio().longValue());
    }

    @Test(expected = MutantException.class)
    public void invalidDna() throws MutantException {
        mutantServicePort.isMutant(new String[]{"ATGCGA", "CAGTC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});
    }

    @Test(expected = MutantException.class)
    public void nullDNA() throws MutantException {
        mutantServicePort.isMutant(null);
    }

    @Test(expected = MutantException.class)
    public void incompleteDNA() throws MutantException {
        mutantServicePort.isMutant(new String[]{"CAGTC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});
    }
}
