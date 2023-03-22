package shop.mtcoding.jobara.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.jobara.board.dto.BoardResp.PagingDto;
import shop.mtcoding.jobara.common.aop.EmployeeCheck;
import shop.mtcoding.jobara.common.aop.EmployeeCheckApi;
import shop.mtcoding.jobara.common.dto.ResponseDto;
import shop.mtcoding.jobara.common.ex.CustomException;
import shop.mtcoding.jobara.employee.dto.EmployeeReq.EmployeeJoinReqDto;
import shop.mtcoding.jobara.employee.dto.EmployeeReq.EmployeeTechUpdateReqDto;
import shop.mtcoding.jobara.employee.dto.EmployeeReq.EmployeeUpdateReqDto;
import shop.mtcoding.jobara.employee.dto.EmployeeResp.EmployeeAndResumeRespDto;
import shop.mtcoding.jobara.employee.dto.EmployeeResp.EmployeeUpdateRespDto;
import shop.mtcoding.jobara.user.vo.UserVo;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/update/{id}")
    @EmployeeCheck
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id, EmployeeUpdateReqDto employeeUpdateReqDto,
            MultipartFile profile) {
        UserVo UserVoPS = employeeService.updateEmpolyee(employeeUpdateReqDto, id, profile);
        return ResponseEntity.status(201).body(UserVoPS);
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "employee/joinForm";
    }

    @PutMapping("/employee/update/tech/{id}")
    @EmployeeCheckApi
    public ResponseEntity<?> update(@PathVariable int id,
            @RequestBody EmployeeTechUpdateReqDto employeeTechUpdateReqDto, Model model) {
        if (employeeTechUpdateReqDto.getCheckedValues() != null) {
            employeeService.updateEmpolyeeTech(employeeTechUpdateReqDto.getCheckedValues(), id);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, " 수정완료", null),
                HttpStatus.valueOf(201));
    }

    @GetMapping("/employee/{id}/resume/{resumeId}")
    public ResponseEntity<?> employeeDetail(@PathVariable int id, @PathVariable int resumeId, Model model) {
        EmployeeAndResumeRespDto employeePS = employeeService.getEmployee(id, resumeId);
        List<String> employeeTechPS = employeeService.getEmployeeTech(id);
        model.addAttribute("employee", employeePS);
        model.addAttribute("employeeTech", employeeTechPS);
        return new ResponseEntity<>(new ResponseDto<>(1, "", model), HttpStatus.valueOf(200));
    }

    @GetMapping("/employee/{id}/updateForm")
    @EmployeeCheck
    public ResponseEntity<?> updateForm(@PathVariable int id, Model model) {

        EmployeeUpdateRespDto employeeUpdateRespDto = employeeService.getEmployeeUpdateRespDto(id);
        List<Integer> employeeSkill = employeeService.getSkillForDetail(id);
        model.addAttribute("employeeDto", employeeUpdateRespDto);
        model.addAttribute("employeeSkill", employeeSkill);
        return ResponseEntity.status(200).body(model);
    }

    @GetMapping("/list")
    public @ResponseBody ResponseEntity<?> employeeList(Model model, Integer page) {
        UserVo principal = (UserVo) model.getAttribute("principal");
        PagingDto pagingPS = employeeService.getEmployee(page);
        model.addAttribute("pagingDto", pagingPS);
        if (principal != null) {
            if (principal.getRole().equals("company")) {
                List<EmployeeAndResumeRespDto> recommendEmployeeListPS = employeeService
                        .getRecommendEmployee(principal.getId());
                model.addAttribute("recommendEmployeeList", recommendEmployeeListPS);
            }
        }

        return ResponseEntity.status(200).body(model);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(EmployeeJoinReqDto employeeJoinReqDto) {
        employeeService.insertEmployee(employeeJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "가입 완료", null), HttpStatus.valueOf(201));
    }

    @GetMapping("/employee/{id}")
    public @ResponseBody ResponseEntity<?> employeeDetail(@PathVariable int id, Model model) {
        EmployeeAndResumeRespDto employeePS = employeeService.getEmployee(id);
        List<String> employeeTechPS = employeeService.getEmployeeTech(id);
        model.addAttribute("employee", employeePS);
        model.addAttribute("employeeTech", employeeTechPS);
        return ResponseEntity.status(200).body(model);
    }
}