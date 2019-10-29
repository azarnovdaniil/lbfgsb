subroutine timer(ttime)
    double precision ttime
    !
    real temp
    !
    !     This routine computes cpu time in double precision; it makes use of
    !     the intrinsic f90 cpu_time therefore a conversion type is
    !     needed.
    !
    !           J.L Morales  Departamento de Matematicas,
    !                        Instituto Tecnologico Autonomo de Mexico
    !                        Mexico D.F.
    !
    !           J.L Nocedal  Department of Electrical Engineering and
    !                        Computer Science.
    !                        Northwestern University. Evanston, IL. USA
    !
    !                        January 21, 2011
    !
    temp = sngl(ttime)
    call cpu_time(temp)
    ttime = dble(temp)

    return

end
      