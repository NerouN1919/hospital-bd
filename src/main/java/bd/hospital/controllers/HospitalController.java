package bd.hospital.controllers;

import bd.hospital.attributes.*;
import bd.hospital.dto.AddPersonToWardDto;
import bd.hospital.dto.AddWardDto;
import bd.hospital.dto.CreateDiagnosisDto;
import bd.hospital.dto.UpdateWardDto;
import bd.hospital.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping()
public class HospitalController {
    private HospitalService hospitalService;

    private static final int PAGE_SIZE = 5;
    @Autowired
    public void setHospitalService(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping()
    public String firstPage(@RequestParam("page") int page, Model model) {
        int wardPagesCount = hospitalService.getWardPagesCount(PAGE_SIZE);

        if ((page <= 0 || page > wardPagesCount) && wardPagesCount != 0) {
            return "redirect:?page=1";
        }
        model.addAttribute("wards", hospitalService.getWardPageInfo(PAGE_SIZE, page));
        model.addAttribute("currentPage", page);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("previousPage", page - 1);
        model.addAttribute("wardPagesCount", wardPagesCount);
        if (model.getAttribute("addPersonAttribute") == null) {
            model.addAttribute("addPersonAttribute", new AddPersonAttribute());
        }
        if (model.getAttribute("addWardAttribute") == null) {
            model.addAttribute("addWardAttribute", new AddWardAttribute());
        }
        if (model.getAttribute("updateWardAttribute") == null) {
            model.addAttribute("updateWardAttribute", new UpdateWardAttribute());
        }
        if (model.getAttribute("updatePersonAttribute") == null) {
            model.addAttribute("updatePersonAttribute", new UpdatePersonAttribute());
        }
        if (model.getAttribute("diagnosisAttribute") == null) {
            model.addAttribute("diagnosisAttribute", new DiagnosisAttribute());
        }
        model.addAttribute("allDiagnoses", hospitalService.getAllDiagnoses());

        boolean hasUserRole = getAuth().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
        boolean hasAdminRole = getAuth().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (hasAdminRole) {
            return "main-info-admin";
        } else if (hasUserRole) {
            return "main-info-user";
        }
        return "";
    }

    @PostMapping("add-person-to-ward")
    public String addPersonToWard(@ModelAttribute AddPersonAttribute addPersonAttribute,
                                  BindingResult bindingResult,
                                  @RequestParam("page") int page,
                                  @RequestParam("wardId") Long wardId,
                                  RedirectAttributes redirectAttributes) {
        addPersonAttribute.setWardId(wardId);
        AddPersonToWardDto addPersonToWardDto = hospitalService.addPersonToWard(addPersonAttribute);
        if (addPersonToWardDto.getResultCode() != null
                && addPersonToWardDto.getResultMessage() != null
                && addPersonToWardDto.getResultCode() == 0) {
            bindingResult.rejectValue(null, addPersonToWardDto.getResultCode().toString(), addPersonToWardDto.getResultMessage());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPersonAttribute", addPersonAttribute);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPersonAttribute", bindingResult);
        }
        return "redirect:?page=" + page;
    }
    @PostMapping("add-ward")
    public String addWard(@ModelAttribute AddWardAttribute addWardAttribute,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        AddWardDto addWardDto = hospitalService.createWard(addWardAttribute);
        if (addWardDto.getResultCode() != null
                && addWardDto.getResultMessage() != null
                && addWardDto.getResultCode() == 0) {
            bindingResult.rejectValue(null, addWardDto.getResultCode().toString(), addWardDto.getResultMessage());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addWardAttribute", addWardAttribute);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addWardAttribute", bindingResult);
        }
        return "redirect:?page=1";
    }

    @PostMapping("update-ward")
    public String updateWard(@ModelAttribute UpdateWardAttribute updateWardAttribute,
                             BindingResult bindingResult,
                             @RequestParam("page") int page,
                             @RequestParam("wardId") Long wardId,
                             RedirectAttributes redirectAttributes) {
        updateWardAttribute.setWardId(wardId);
        UpdateWardDto updateWardDto = hospitalService.updateWard(updateWardAttribute);
        if (updateWardDto.getResultCode() != null
                && updateWardDto.getResultMessage() != null
                && updateWardDto.getResultCode() == 0) {
            bindingResult.rejectValue(null, updateWardDto.getResultCode().toString(), updateWardDto.getResultMessage());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateWardAttribute", updateWardAttribute);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateWardAttribute", bindingResult);
        }
        return "redirect:?page=" + page;
    }

    @PostMapping("update-person")
    public String updatePerson(@ModelAttribute UpdatePersonAttribute updatePersonAttribute,
                             BindingResult bindingResult,
                             @RequestParam("page") int page,
                             @RequestParam("personId") Long personId,
                             RedirectAttributes redirectAttributes) {
        updatePersonAttribute.setId(personId);
        try {
            hospitalService.updatePerson(updatePersonAttribute);
        } catch (Exception e) {
            bindingResult.rejectValue(null, "Err", e.getMessage());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateWardAttribute", updatePersonAttribute);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateWardAttribute", bindingResult);
        }
        return "redirect:?page=" + page;
    }

    @GetMapping("delete-person")
    public String deletePerson(@RequestParam("personId") Long personId) {
        hospitalService.deletePerson(personId);
        return "redirect:?page=1";
    }

    @GetMapping("delete-ward")
    public String deleteWard(@RequestParam("wardId") Long wardId) {
        hospitalService.deleteWard(wardId);
        return "redirect:?page=1";
    }

    @PostMapping("create-diagnosis")
    public String createDiagnosis(@ModelAttribute("diagnosisAttribute") DiagnosisAttribute diagnosisAttribute,
                                  BindingResult bindingResult,
                                  @RequestParam("page") int page,
                                  RedirectAttributes redirectAttributes) {
        CreateDiagnosisDto createDiagnosisDto = hospitalService.createDiagnosis(diagnosisAttribute);
        if (createDiagnosisDto.getResultCode() != null
                && createDiagnosisDto.getResultMessage() != null
                && createDiagnosisDto.getResultCode() == 0) {
            bindingResult.rejectValue(null, createDiagnosisDto.getResultCode().toString(), createDiagnosisDto.getResultMessage());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("diagnosisAttribute", diagnosisAttribute);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.diagnosisAttribute", bindingResult);
        }
        return "redirect:?page=" + page;
    }

    @GetMapping("statistic")
    public String getStatistic(Model model) {
        model.addAttribute("statistics", hospitalService.getStatistic());
        return "statistic";
    }
}
