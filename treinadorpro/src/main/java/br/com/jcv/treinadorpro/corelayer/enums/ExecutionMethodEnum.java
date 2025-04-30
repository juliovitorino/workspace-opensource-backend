package br.com.jcv.treinadorpro.corelayer.enums;

public enum ExecutionMethodEnum {
    TRADITIONAL_SET("Traditional Set", "Série Tradicional", "Serie Tradicional"),
    DROP_SET("Drop Set", "Drop Set", "Drop Set"),
    BI_SET("Bi-set", "Bi-set", "Bi-set"),
    TRI_SET("Tri-set", "Tri-set", "Tri-set"),
    GIANT_SET("Giant Set", "Giant Set", "Serie Gigante"),
    REST_PAUSE("Rest-Pause", "Rest-Pause", "Pausa Descanso"),
    ISOMETRIC("Isometric Hold", "Isometria", "Isometría"),
    TIME_UNDER_TENSION("Time Under Tension", "Tempo Sob Tensão", "Tiempo Bajo Tensión"),
    PARTIAL_REPS("Partial Repetitions", "Repetições Parciais", "Repeticiones Parciales"),
    NEGATIVE_REPS("Negative (Eccentric)", "Negativa (Excêntrica)", "Negativa (Excéntrica)"),
    PYRAMID_SET("Pyramid Set", "Série Pirâmide", "Serie Piramidal"),
    SUPER_SLOW("Super Slow", "Execução Lenta", "Ejecución Lenta"),
    PRE_EXHAUSTION("Pre-exhaustion", "Pré-exaustão", "Pre-agotamiento"),
    POST_EXHAUSTION("Post-exhaustion", "Pós-exaustão", "Post-agotamiento"),
    CLUSTER_SET("Cluster Set", "Série Cluster", "Serie de Clúster"),
    CIRCUIT("Circuit Training", "Treino em Circuito", "Entrenamiento en Circuito"),
    FST_7("FST-7", "FST-7", "FST-7");

    private final String nameEn;
    private final String namePt;
    private final String nameEs;

    ExecutionMethodEnum(String nameEn, String namePt, String nameEs) {
        this.nameEn = nameEn;
        this.namePt = namePt;
        this.nameEs = nameEs;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNamePt() {
        return namePt;
    }

    public String getNameEs() {
        return nameEs;
    }
}
