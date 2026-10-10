package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double goalRotation;
    double currentRotation;
    double error;
    double power;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        goalRotation = pose.getCurrentHeading().getDegrees() + 90;
    }

    @Override
    public void execute() {
        currentRotation = pose.getCurrentHeading().getDegrees();
        error = goalRotation - currentRotation;
        power = 0.005 * error;
        drive.tankDrive(-power, power);
    }

    @Override
    public boolean isFinished(){
        return (error <= 1);
    }
}
