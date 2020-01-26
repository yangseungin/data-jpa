package study.datajpa.repository;

public interface NestedClosedProjections {

    String getuserName();
    TeamInfo getTeam();

    interface TeamInfo{
        String getName();
    }
}
