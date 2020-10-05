package br.com.sicredi.sincronizacontas.batch;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

@Component
public class ContaHeaderWriter implements FlatFileHeaderCallback {
    
    private String header;

    public ContaHeaderWriter(){
        this(";");
    }
    public ContaHeaderWriter(String delimiter){
        this.header = (Arrays.asList(new String[] {"agencia", "numero","saldo", "status", "sincronizado"}))
        .stream()
        .collect(Collectors.joining(delimiter));
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(this.getHeader());

    }
}
