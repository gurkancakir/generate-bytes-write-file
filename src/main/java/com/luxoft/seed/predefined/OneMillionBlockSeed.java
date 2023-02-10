package com.luxoft.seed.predefined;

import com.luxoft.seed.Seed;

import java.util.ArrayList;
import java.util.List;

public class OneMillionBlockSeed implements Seed {
    private static final List<Long> seeds = new ArrayList<>();

    static {
        seeds.add(25214903917L);
        seeds.add(10047353266109L);
        seeds.add(203257151221005L);
        seeds.add(152301230706269L);
        seeds.add(14929897800621L);
        seeds.add(121943617490173L);
        seeds.add(56701259934285L);
        seeds.add(28845257431965L);
        seeds.add(96918160990445L);
        seeds.add(183263224052285L);
        seeds.add(235795687876493L);
        seeds.add(243075163957469L);
        seeds.add(160953229753901L);
        seeds.add(160914074888061L);
        seeds.add(273219895600333L);
        seeds.add(257191339685405L);
        seeds.add(38372094892909L);
        seeds.add(278270527820989L);
        seeds.add(179465234113037L);
        seeds.add(113804243239773L);
        seeds.add(124772739397805L);
        seeds.add(62171693187581L);
        seeds.add(140744125144909L);
        seeds.add(221804512956573L);
        seeds.add(127945272317421L);
        seeds.add(275038241746749L);
        seeds.add(5793008884877L);
        seeds.add(86701914709469L);
        seeds.add(137971620492077L);
        seeds.add(132283352685693L);
        seeds.add(250418919446989L);
        seeds.add(207557777730333L);
        seeds.add(78844104200301L);
        seeds.add(219591940016573L);
        seeds.add(197665037408013L);
        seeds.add(103636500658269L);
        seeds.add(74877687115181L);
        seeds.add(132544065648381L);
        seeds.add(248994660471885L);
        seeds.add(70600499004829L);
        seeds.add(43066040306413L);
        seeds.add(127256770585661L);
        seeds.add(245194244362637L);
        seeds.add(192324129690333L);
        seeds.add(200231280551981L);
        seeds.add(26644883198333L);
        seeds.add(149445001580237L);
        seeds.add(267501289889821L);
        seeds.add(233167569274221L);
        seeds.add(167375408643773L);
        seeds.add(237813290912781L);
        seeds.add(88096736767325L);
        seeds.add(228003315917485L);
        seeds.add(165390848781309L);
        seeds.add(234209844283725L);
        seeds.add(69641128098461L);
        seeds.add(262557241950189L);
        seeds.add(3601079949629L);
        seeds.add(204567598040717L);
        seeds.add(263044946806749L);
        seeds.add(69063189044525L);
        seeds.add(64044068079229L);
        seeds.add(114013384868813L);
        seeds.add(281206099809565L);
        seeds.add(174638554987117L);
        seeds.add(104515421139901L);
        seeds.add(17358323302669L);
        seeds.add(140411191173725L);
        seeds.add(15896729950125L);
        seeds.add(275891522740477L);
        seeds.add(38481974705741L);
        seeds.add(46205632606109L);
        seeds.add(64577950889197L);
        seeds.add(239496572931645L);
        seeds.add(138610267716493L);
        seeds.add(249632498121949L);
        seeds.add(82899226169901L);
        seeds.add(43688233450365L);
        seeds.add(236437143582925L);
        seeds.add(129415192758813L);
        seeds.add(264022771478381L);
        seeds.add(226387087009981L);
        seeds.add(117139968964109L);
        seeds.add(159258632574813L);
        seeds.add(191093376224429L);
        seeds.add(17650003793405L);
        seeds.add(256188623041357L);
        seeds.add(4869494875293L);
        seeds.add(118444327807469L);
        seeds.add(116211879494461L);
        seeds.add(180248538411149L);
        seeds.add(69044933145053L);
        seeds.add(71372266374957L);
        seeds.add(188371560034429L);
        seeds.add(276152206683597L);
        seeds.add(10905292341021L);
        seeds.add(142180690601069L);
        seeds.add(96421207694781L);
        seeds.add(11064731020045L);
        seeds.add(150245335469149L);
    }


    @Override
    public long get(int index) {
        return seeds.get(index);
    }

    @Override
    public boolean support(int chunkSize) {
        return 1_000_000 == chunkSize;
    }
}
