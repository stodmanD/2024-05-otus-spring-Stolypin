package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.OfficeUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employeemanagement.dto.OfficeDto;
import com.example.employeemanagement.exception.EntityNotFoundException;
import com.example.employeemanagement.model.Office;
import com.example.employeemanagement.repository.OfficeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;


    @Transactional
    public OfficeDto create(OfficeUpdateDto officeUpdateDto) {

        Office office = new Office(officeUpdateDto.address(), officeUpdateDto.capacity(), officeUpdateDto.description());

        Office savedOffice = officeRepository.save(office);

        return OfficeDto.fromEntity(savedOffice);
    }

    @Transactional(readOnly = true)
    public OfficeDto findById(long id) {
        return officeRepository.findById(id)
                .map(OfficeDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Office with id=%d not found".formatted(id)));
    }

    @Transactional
    public OfficeDto update(long id, String address, int capacity, String description) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Office with id=%d not found".formatted(id)));
        office.setAddress(address);
        office.setCapacity(capacity);
        office.setDescription(description);

        Office updatedOffice = officeRepository.save(office);
        return OfficeDto.fromEntity(updatedOffice);
    }

    @Transactional
    public void delete(long id) {
        officeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OfficeDto> findAll() {
        return officeRepository.findAll()
                .stream().map(OfficeDto::fromEntity)
                .toList();
    }

}
