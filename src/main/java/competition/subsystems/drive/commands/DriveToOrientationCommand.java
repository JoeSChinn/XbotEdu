package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double targetRotation;
    double currentRotation;
    double error;
    double power;
    double prevError;
    double velocity;
    double rotationNecessary;
    double necessaryTarget;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetHeading(double heading) {
        targetRotation = heading;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        if (targetRotation <= 0) {
            necessaryTarget = 360 + targetRotation;
        }
        else {
            necessaryTarget = targetRotation;
        }
        rotationNecessary = Math.abs(pose.getCurrentHeading().getDegrees() - necessaryTarget);
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        currentRotation = pose.getCurrentHeading().getDegrees();
        error = currentRotation - targetRotation;
        velocity = prevError - error;
        if (rotationNecessary <= 180) {
            power = 0.04 * Math.abs(error) + 1 * velocity;
            drive.tankDrive(-power, power);
        }
        else{
            power = 0.04 * Math.abs(error) - 1 * velocity;
            drive.tankDrive(power,-power);
        }

        prevError = error;
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        return (Math.abs(error) <= 0.1 && velocity <= 0.05);
    }
}
