package shop.mtcoding.jobara.employee.dto;

import shop.mtcoding.jobara.employee.dto.EmployeeReq.EmployeeUpdateReqDto;

public class EmployeeUpdateBuilder extends EmployeeUpdateReqDto {

    public static EmployeeUpdateReqDto makeUpdateReqDto(String password, String email, String address,
            String detailAddress, String tel, String realName, String education, Integer career) {
        EmployeeUpdateReqDto makeUpdateReqDto = new EmployeeUpdateReqDto();
        makeUpdateReqDto.setPassword(password);
        makeUpdateReqDto.setEmail(email);
        makeUpdateReqDto.setAddress(address);
        makeUpdateReqDto.setDetailAddress(detailAddress);
        makeUpdateReqDto.setTel(tel);
        makeUpdateReqDto.setEmployeeDto(new EmployeeDto(realName, education, career));

        return makeUpdateReqDto;
    }

}
