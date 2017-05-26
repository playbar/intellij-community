/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.python;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.command.WriteCommandAction;
import com.jetbrains.python.fixtures.PyTestCase;
import com.jetbrains.python.psi.PyFunction;
import com.jetbrains.python.structureView.PyStructureViewElement;

import javax.swing.*;

import static com.intellij.testFramework.PlatformTestUtil.assertTreeEqual;

/**
 * @author vlan
 */
public class PyStructureViewTest extends PyTestCase {
  private static final String TEST_DIRECTORY = "structureView/";

  public void testBaseClassNames() {
    myFixture.configureByFiles(TEST_DIRECTORY + "baseClassNames.py",
                               TEST_DIRECTORY + "lib1.py");
    doTest("-baseClassNames.py\n" +
           " -B1\n" +
           "  f(self, x)\n" +
           " -B2(object)\n" +
           "  g(x)\n" +
           " C(B1, B2)\n" +
           " D1(C)\n" +
           " D2(C)\n" +
           " D3(lib1.C)\n" +
           " D4(foo.bar.C)\n",
           false);
  }

  // PY-3371
  public void testAttributes() {
    myFixture.configureByFile(TEST_DIRECTORY + "attributes.py");
    doTest("-attributes.py\n" +
           " -B(object)\n" +
           "  f(self, x)\n" +
           "  __init__(self, x, y)\n" +
           "  g(cls, x)\n" +
           "  c1\n" +
           "  c2\n" +
           "  i1\n" +
           "  i2\n" +
           "  i3\n" +
           " g1\n" +
           " -C(B)\n" +
           "  __init__(self, x, y)\n" +
           "  h(self)\n" +
           "  c2\n" +
           "  c3\n" +
           "  i3\n" +
           "  i4\n" +
           "  i5\n" +
           " g2\n",
           false);
  }

  // PY-3936
  public void testInherited() {
    myFixture.configureByFile(TEST_DIRECTORY + "inherited.py");
    doTest("-inherited.py\n" +
           " -C(object)\n" +
           "  f(self, x)\n" +
           "  __str__(self)\n" +
           "  x\n" +
           "  __init__(self)\n" +
           "  __new__(cls)\n" +
           "  __setattr__(self, name, value)\n" +
           "  __eq__(self, o)\n" +
           "  __ne__(self, o)\n" +
           "  __repr__(self)\n" +
           "  __hash__(self)\n" +
           "  __format__(self, format_spec)\n" +
           "  __getattribute__(self, name)\n" +
           "  __delattr__(self, name)\n" +
           "  __sizeof__(self)\n" +
           "  __reduce__(self)\n" +
           "  __reduce_ex__(self, protocol)\n" +
           "  __class__\n" +
           "  __doc__\n" +
           "  __module__\n" +
           "  __slots__\n",
           true);
  }

  // EA-83566
  public void testInvalidatedElement() {
    myFixture.configureByText("a.py",
                              "def f():\n" +
                              "    pass");
    final PyFunction function = myFixture.findElementByText("f", PyFunction.class);
    final PyStructureViewElement node = new PyStructureViewElement(function);
    WriteCommandAction.runWriteCommandAction(myFixture.getProject(), function::delete);
    assertNull(node.getValue());
    final ItemPresentation presentation = node.getPresentation();
    assertNotNull(presentation);
    final Icon icon = presentation.getIcon(false);
    assertNull(icon);
  }

  private void doTest(final String expected, final boolean inherited) {
    myFixture.testStructureView(component -> {
      component.setActionActive("SHOW_INHERITED", !inherited);
      assertTreeEqual(component.getTree(), expected);
    });
  }
}
