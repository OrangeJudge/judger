package judger.languages;

import utils.OJException;

public abstract class Language {
    public boolean timeLimitExceeded;
    public abstract void compile() throws OJException;
    public abstract void execute(int timeLimit, int memoryLimit) throws OJException;
}