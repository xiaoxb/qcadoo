package com.qcadoo.model.api.utils;

import static com.qcadoo.model.api.types.TreeType.NODE_NUMBER_FIELD;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.Lists;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTree;
import com.qcadoo.model.api.EntityTreeNode;
import com.qcadoo.model.internal.PriorityServiceImpl;
import com.qcadoo.model.internal.api.PriorityService;

public class TreeNumberingServiceTest {

    private TreeNumberingService treeNumberingService;

    private EntityTree tree;

    private EntityTreeNode rootNode;

    @Before
    public void init() {
        treeNumberingService = new TreeNumberingServiceImpl();

        rootNode = mock(EntityTreeNode.class);

        tree = mock(EntityTree.class);
        when(tree.getRoot()).thenReturn(rootNode);
        when(rootNode.getField("priority")).thenReturn(1L);

        PriorityService priorityService = mock(PriorityService.class);
        when(priorityService.getEntityPriorityComparator()).thenReturn(new Comparator<Entity>() {

            @Override
            public int compare(final Entity n1, final Entity n2) {
                Integer p1 = (Integer) n1.getField("priority");
                Integer p2 = (Integer) n2.getField("priority");
                return p1.compareTo(p2);
            }
        });

        ReflectionTestUtils.setField(treeNumberingService, "priorityService", new PriorityServiceImpl());
    }

    @Test
    public void shouldNumberSequentialTree() throws Exception {
        // given
        EntityTreeNode node1 = getTreeNodeMock();
        EntityTreeNode node2 = getTreeNodeMock();
        EntityTreeNode node3 = getTreeNodeMock();

        when(rootNode.getChildren()).thenReturn(Lists.newArrayList(node1));
        when(node1.getChildren()).thenReturn(Lists.newArrayList(node2));
        when(node2.getChildren()).thenReturn(Lists.newArrayList(node3));

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        // when
        treeNumberingService.generateTreeNumbers(tree);

        // then
        Mockito.verify(rootNode).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.", argument.getValue());

        Mockito.verify(node1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("2.", argument.getValue());

        Mockito.verify(node2).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("3.", argument.getValue());

        Mockito.verify(node3).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("4.", argument.getValue());
    }

    @Test
    public void shouldGenerateNumberForEntityTreeNode() throws Exception {
        // given
        EntityTreeNode node1 = getTreeNodeMock();

        when(rootNode.getChildren()).thenReturn(Lists.newArrayList(node1));

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        // when
        treeNumberingService.generateTreeNumbers(rootNode);

        // then
        Mockito.verify(rootNode).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.", argument.getValue());

        Mockito.verify(node1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("2.", argument.getValue());
    }
    
    @Test
    public void shouldNumberBranchedTree() throws Exception {
        // given
        EntityTreeNode node1A1 = getTreeNodeMock();
        EntityTreeNode node1B1 = getTreeNodeMock(2);

        EntityTreeNode node1A2 = getTreeNodeMock();
        EntityTreeNode node1A3 = getTreeNodeMock();

        EntityTreeNode node1B1A1 = getTreeNodeMock();
        EntityTreeNode node1B1A2 = getTreeNodeMock();
        EntityTreeNode node1B1A2A1 = getTreeNodeMock();
        EntityTreeNode node1B1A2B1 = getTreeNodeMock();

        EntityTreeNode node1B1B1 = getTreeNodeMock(2);
        EntityTreeNode node1B1B2 = getTreeNodeMock();
        EntityTreeNode node1B1B3 = getTreeNodeMock();

        when(rootNode.getChildren()).thenReturn(Lists.newArrayList(node1A1, node1B1));
        when(node1A1.getChildren()).thenReturn(Lists.newArrayList(node1A2));
        when(node1A2.getChildren()).thenReturn(Lists.newArrayList(node1A3));

        when(node1B1.getChildren()).thenReturn(Lists.newArrayList(node1B1A1, node1B1B1));
        when(node1B1A1.getChildren()).thenReturn(Lists.newArrayList(node1B1A2));
        when(node1B1A2.getChildren()).thenReturn(Lists.newArrayList(node1B1A2A1, node1B1A2B1));

        when(node1B1B1.getChildren()).thenReturn(Lists.newArrayList(node1B1B2));
        when(node1B1B2.getChildren()).thenReturn(Lists.newArrayList(node1B1B3));

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        // when
        treeNumberingService.generateTreeNumbers(tree);

        // then
        Mockito.verify(rootNode).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.", argument.getValue());

        Mockito.verify(node1A1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.A.1.", argument.getValue());

        Mockito.verify(node1B1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.", argument.getValue());

        Mockito.verify(node1A2).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.A.2.", argument.getValue());

        Mockito.verify(node1A3).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.A.3.", argument.getValue());

        Mockito.verify(node1B1A1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.A.1.", argument.getValue());

        Mockito.verify(node1B1A2).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.A.2.", argument.getValue());

        Mockito.verify(node1B1A2A1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.A.2.A.1.", argument.getValue());

        Mockito.verify(node1B1A2B1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.A.2.B.1.", argument.getValue());

        Mockito.verify(node1B1B1).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.B.1.", argument.getValue());

        Mockito.verify(node1B1B2).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.B.2.", argument.getValue());

        Mockito.verify(node1B1B3).setField(Mockito.eq(NODE_NUMBER_FIELD), argument.capture());
        assertEquals("1.B.1.B.3.", argument.getValue());
    }

    @Test
    public void shouldDoNothingIfTreeIsEmpty() throws Exception {
        // given
        when(tree.getRoot()).thenReturn(null);

        // when
        treeNumberingService.generateTreeNumbers(tree);

        // then
        Mockito.verify(rootNode, Mockito.times(0)).setField(Mockito.eq(NODE_NUMBER_FIELD), Mockito.anyString());

    }

    private EntityTreeNode getTreeNodeMock() {
        return getTreeNodeMock(1);
    }

    private EntityTreeNode getTreeNodeMock(final Integer priority) {
        EntityTreeNode node = mock(EntityTreeNode.class);
        when(node.getField("priority")).thenReturn(priority);
        return node;
    }

}