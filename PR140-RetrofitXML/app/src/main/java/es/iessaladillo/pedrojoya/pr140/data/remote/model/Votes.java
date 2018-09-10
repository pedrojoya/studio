package es.iessaladillo.pedrojoya.pr140.data.remote.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("unused")
@Root(name = "votos")
public class Votes {

    @Element(name = "nulos")
    private NullVotes nullVotes;
    @Element(name = "contabilizados")
    private CountedVotes countedVotes;
    @Element(name = "blancos")
    private BlankVotes blankVotes;
    @Element(name = "abstenciones")
    private AbstentionsVotes abstentionVotes;

    public NullVotes getNullVotes() {
        return nullVotes;
    }

    public void setNullVotes(NullVotes nullVotes) {
        this.nullVotes = nullVotes;
    }

    public CountedVotes getCountedVotes() {
        return countedVotes;
    }

    public void setCountedVotes(CountedVotes countedVotes) {
        this.countedVotes = countedVotes;
    }

    public BlankVotes getBlankVotes() {
        return blankVotes;
    }

    public void setBlankVotes(BlankVotes blankVotes) {
        this.blankVotes = blankVotes;
    }

    public AbstentionsVotes getAbstentionVotes() {
        return abstentionVotes;
    }

    public void setAbstentionVotes(AbstentionsVotes abstentionVotes) {
        this.abstentionVotes = abstentionVotes;
    }

}
